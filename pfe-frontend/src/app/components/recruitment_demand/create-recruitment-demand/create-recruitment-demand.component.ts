import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RecruitmentDemandPayload } from '../recruitment-demand-payload';
import { Router } from '@angular/router';
import { RecruitmentDemandService } from 'src/app/services/recruitment_demand/recruitment-demand.service';

@Component({
  selector: 'app-create-recruitment-demand',
  templateUrl: './create-recruitment-demand.component.html',
  styleUrls: ['./create-recruitment-demand.component.css']
})
export class CreateRecruitmentDemandComponent {

  createRecruitmentDemandForm: FormGroup = new FormGroup({});
  recruitmentDemandPayload: RecruitmentDemandPayload;
  isNotValid: boolean = false;
  isError: boolean = false;

  constructor(private router: Router, private recruitmentDemandService: RecruitmentDemandService) {
    this.recruitmentDemandPayload = {
      postTitle: '',
      postDescription: '',
      numberOfYearsOfExperience: 0,
      numberOfProfiles: 0,
      levelOfStudies: '',
      statusOfDemand: '',
      dateOfDemand: new Date(2001, 1, 1)
    }
  }

  ngOnit() {
    this.createRecruitmentDemandForm = new FormGroup({
      postTitle: new FormControl('', Validators.required),
      postDescription: new FormControl('', Validators.required),
      numberOfYearsOfExperience: new FormControl('', Validators.required),
      numberOfProfiles: new FormControl('', Validators.required),
      levelOfStudies: new FormControl('', Validators.required),
      statusOfDemand: new FormControl('', Validators.required),
      dateOfDemand: new FormControl('', Validators.required)
    });
  }

  createRecruitmentDemand() {
    this.recruitmentDemandPayload.postTitle = this.createRecruitmentDemandForm.get('postTitle')?.value;
    this.recruitmentDemandPayload.postDescription = this.createRecruitmentDemandForm.get('postDescription')?.value;
    this.recruitmentDemandPayload.numberOfYearsOfExperience = this.createRecruitmentDemandForm.get('numberOfYearsOfExperience')?.value;
    this.recruitmentDemandPayload.numberOfProfiles = this.createRecruitmentDemandForm.get('numberOfProfiles')?.value;
    this.recruitmentDemandPayload.levelOfStudies = this.createRecruitmentDemandForm.get('levelOfStudies')?.value;
    this.recruitmentDemandPayload.statusOfDemand = this.createRecruitmentDemandForm.get('statusOfDemand')?.value;
    this.recruitmentDemandPayload.dateOfDemand = this.createRecruitmentDemandForm.get('dateOfDemand')?.value;

    if (this.recruitmentDemandPayload.postTitle === '' || this.recruitmentDemandPayload.postDescription === '' || this.recruitmentDemandPayload.numberOfYearsOfExperience === 0 || this.recruitmentDemandPayload.numberOfProfiles === 0 || this.recruitmentDemandPayload.levelOfStudies === '' || this.recruitmentDemandPayload.statusOfDemand === '' || this.recruitmentDemandPayload.dateOfDemand === new Date(2001, 1, 1)) {
      this.isNotValid = true;
    } else {
      this.recruitmentDemandService.createRecruitmentDemand(this.recruitmentDemandPayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/recruitment_demands');
        },
        error: () => {
          this.isError = true
        }
      })
    }
  }
}
