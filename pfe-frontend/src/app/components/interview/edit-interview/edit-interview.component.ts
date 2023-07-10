import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InterviewService } from 'src/app/services/interview/interview.service';
import { InterviewPayload } from '../interview-payload';

@Component({
  selector: 'app-edit-interview',
  templateUrl: './edit-interview.component.html',
  styleUrls: ['./edit-interview.component.css']
})
export class EditInterviewComponent {

  editInterviewForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  interviewId!: number;

  constructor(private route: ActivatedRoute, private router: Router, private interviewService: InterviewService) {
    this.editInterviewForm = new FormGroup({
      interviewTitle: new FormControl('', Validators.required),
      interviewDate: new FormControl('', Validators.required),
    });
  }

  ngOnInit() {
    this.interviewId = Number(this.route.snapshot.paramMap.get('id'));
    this.interviewService.getInterviewById(this.interviewId).subscribe({
      next: (response) => {
        this.editInterviewForm.setValue({
          interviewTitle: response.interviewTitle,
          interviewDate: response.interviewDate,
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  editInterview() {
    if (this.editInterviewForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const interviewPayload: InterviewPayload = {
      interviewTitle: this.editInterviewForm.value.interviewTitle,
      interviewDate: this.editInterviewForm.value.interviewDate,
    };

    this.interviewService.updateInterview(interviewPayload, this.interviewId).subscribe({
      next: () => {
        this.router.navigateByUrl('/interviews');
      },
      error: () => {
        this.isError = true;
      }
    });
  }
}
