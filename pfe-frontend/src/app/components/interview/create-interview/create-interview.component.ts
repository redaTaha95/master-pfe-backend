import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { InterviewPayload } from '../interview-payload';
import { Router } from '@angular/router';
import { InterviewService } from 'src/app/services/interview/interview.service';

@Component({
  selector: 'app-create-interview',
  templateUrl: './create-interview.component.html',
  styleUrls: ['./create-interview.component.css']
})
export class CreateInterviewComponent {

  createInterviewForm: FormGroup = new FormGroup({});
  interviewPayload: InterviewPayload;
  isNotValid: boolean = false;
  isError: boolean = false;

  constructor(private router: Router, private interviewService: InterviewService) {
    this.interviewPayload = {
      interviewTitle: '',
      interviewDate: new Date(2001, 1, 1),
    }
  }

  ngOnit() {
    this.createInterviewForm = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
    });
  }

  createInterview() {
    this.interviewPayload.interviewTitle = this.createInterviewForm.get('interviewTitle')?.value;
    this.interviewPayload.interviewDate = this.createInterviewForm.get('interviewDate')?.value;

    if (this.interviewPayload.interviewTitle === '' || this.interviewPayload.interviewDate === new Date(2001, 1, 1)) {
      this.isNotValid = true;
    } else {
      this.interviewService.createInterview(this.interviewPayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/interviews');
        },
        error: () => {
          this.isError = true
        }
      })
    }
  }
}
