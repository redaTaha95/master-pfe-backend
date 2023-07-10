import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CandidateService } from 'src/app/services/candidate/candidate.service';
import { CandidatePayload } from '../candidate-payload';

@Component({
  selector: 'app-edit-candidate',
  templateUrl: './edit-candidate.component.html',
  styleUrls: ['./edit-candidate.component.css']
})
export class EditCandidateComponent {
  
  editCandidateForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  candidateId!: number;

  constructor(private route: ActivatedRoute, private router: Router, private candidateService: CandidateService) {
    this.editCandidateForm = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      phone: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      sector: new FormControl('', Validators.required),
      numberOfYearsOfExperience : new FormControl('', Validators.required),
      levelOfStudies: new FormControl('', Validators.required)
    });
  }

  ngOnInit() {
    this.candidateId = Number(this.route.snapshot.paramMap.get('id'));
    this.candidateService.getCandidateById(this.candidateId).subscribe({
      next: (response) => {
        this.editCandidateForm.setValue({
          firstName: response.firstName,
          lastName: response.lastName,
          email: response.email,
          phone: response.phone,
          address: response.address,
          sector: response.sector,
          numberOfYearsOfExperience: response.numberOfYearsOfExperience,
          levelOfStudies: response.levelOfStudies,
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  editCandidate() {
    if (this.editCandidateForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const candidatePayload: CandidatePayload = {
      firstName: this.editCandidateForm.value.firstName,
      lastName: this.editCandidateForm.value.lastName,
      email: this.editCandidateForm.value.email,
      phone: this.editCandidateForm.value.phone,
      address: this.editCandidateForm.value.address,
      sector: this.editCandidateForm.value.sector,
      numberOfYearsOfExperience: this.editCandidateForm.value.numberOfYearsOfExperience,
      levelOfStudies: this.editCandidateForm.value.levelOfStudies
    };

    this.candidateService.updateCandidate(candidatePayload, this.candidateId).subscribe({
      next: () => {
        this.router.navigateByUrl('/candidates');
      },
      error: () => {
        this.isError = true;
      }
    });
  }
}
