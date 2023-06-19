import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RecruitmentDemandService } from 'src/app/services/recruitment_demand/recruitment-demand.service';
import { RecruitmentDemandPayload } from '../recruitment-demand-payload';

@Component({
  selector: 'app-edit-recruitment-demand',
  templateUrl: './edit-recruitment-demand.component.html',
  styleUrls: ['./edit-recruitment-demand.component.css']
})
export class EditRecruitmentDemandComponent {

  editRecruitmentDemandForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  recruitmentDemandId!: number;

  constructor(private route: ActivatedRoute, private router: Router, private recruitmentDemandService: RecruitmentDemandService) {
    this.editRecruitmentDemandForm = new FormGroup({
      postTitle: new FormControl('', Validators.required),
      postDescription: new FormControl('', Validators.required),
      numberOfYearsOfExperience: new FormControl('', Validators.required),
      numberOfProfiles: new FormControl('', Validators.required),
      levelOfStudies: new FormControl('', Validators.required),
      statusOfDemand: new FormControl('', Validators.required),
      dateOfDemand: new FormControl('', Validators.required)
    });
  }

  ngOnInit() {
    this.recruitmentDemandId = Number(this.route.snapshot.paramMap.get('id'));
    this.recruitmentDemandService.getRecruitmentDemandById(this.recruitmentDemandId).subscribe({
      next: (response) => {
        this.editRecruitmentDemandForm.setValue({
          postTitle: response.postTitle,
          postDescription: response.postDescription,
          numberOfYearsOfExperience: response.numberOfYearsOfExperience,
          numberOfProfiles: response.numberOfProfiles,
          levelOfStudies: response.levelOfStudies,
          statusOfDemand: response.statusOfDemand,
          dateOfDemand: response.dateOfDemand,
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  editRecruitmentDemand() {
    if (this.editRecruitmentDemandForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const recruitmentDemandPayload: RecruitmentDemandPayload = {
      postTitle: this.editRecruitmentDemandForm.value.postTitle,
      postDescription: this.editRecruitmentDemandForm.value.postDescription,
      numberOfYearsOfExperience: this.editRecruitmentDemandForm.value.numberOfYearsOfExperience,
      numberOfProfiles: this.editRecruitmentDemandForm.value.numberOfProfiles,
      levelOfStudies: this.editRecruitmentDemandForm.value.levelOfStudies,
      statusOfDemand: this.editRecruitmentDemandForm.value.statusOfDemand,
      dateOfDemand: this.editRecruitmentDemandForm.value.dateOfDemand,
    };

    this.recruitmentDemandService.updateRecruitmentDemand(recruitmentDemandPayload, this.recruitmentDemandId).subscribe({
      next: () => {
        this.router.navigateByUrl('/recruitment_demands');
      },
      error: () => {
        this.isError = true;
      }
    });
  }
}
