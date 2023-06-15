import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CandidatePayload } from '../candidate-payload';
import { Router } from '@angular/router';
import { CandidateService } from 'src/app/services/candidate/candidate.service';

@Component({
  selector: 'app-create-candidate',
  templateUrl: './create-candidate.component.html',
  styleUrls: ['./create-candidate.component.css']
})
export class CreateCandidateComponent {

  createCandidateForm: FormGroup = new FormGroup({});
  candidatePayload: CandidatePayload;
  isNotValid: boolean = false;
  isError: boolean = false;

  constructor(private router: Router, private candidateService: CandidateService) {
    this.candidatePayload = {
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      address: '',
      sector: '',
      numberOfYearsOfExperience: 0,
      levelOfStudies: '',
    }
  }

  ngOnit() {
    this.createCandidateForm = new FormGroup({
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

  createCandidate() {
    this.candidatePayload.firstName = this.createCandidateForm.get('firstName')?.value;
    this.candidatePayload.lastName = this.createCandidateForm.get('lastName')?.value;
    this.candidatePayload.email = this.createCandidateForm.get('email')?.value;
    this.candidatePayload.phone = this.createCandidateForm.get('phone')?.value;
    this.candidatePayload.address = this.createCandidateForm.get('address')?.value;
    this.candidatePayload.sector = this.createCandidateForm.get('sector')?.value;
    this.candidatePayload.numberOfYearsOfExperience = this.createCandidateForm.get('numberOfYearsOfExperience')?.value;
    this.candidatePayload.levelOfStudies = this.createCandidateForm.get('levelOfStudies')?.value;

    if (this.candidatePayload.firstName === '' || this.candidatePayload.lastName === '' || this.candidatePayload.email === '' || this.candidatePayload.phone === '' || this.candidatePayload.address === '' || this.candidatePayload.sector === '' || this.candidatePayload.numberOfYearsOfExperience === 0 || this.candidatePayload.levelOfStudies === '') {
      this.isNotValid = true;
    } else {
      this.candidateService.createCandidate(this.candidatePayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/candidates');
        },
        error: () => {
          this.isError = true
        }
      })
    }
  }
}
