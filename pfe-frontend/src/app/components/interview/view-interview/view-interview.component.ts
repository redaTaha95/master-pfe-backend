import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Interview } from 'src/app/services/interview/Interview';
import { InterviewService } from 'src/app/services/interview/interview.service';

@Component({
  selector: 'app-view-interview',
  templateUrl: './view-interview.component.html',
  styleUrls: ['./view-interview.component.css']
})
export class ViewInterviewComponent {

  interview: Interview = new Interview();
  interviewId!: number;

  constructor(private route:ActivatedRoute, private router: Router, private interviewService: InterviewService) {}

  ngOnInit() {
    this.interviewId = Number(this.route.snapshot.paramMap.get('id'));
    this.interviewService.getInterviewById(this.interviewId).subscribe({
      next: (response) => {
        this.interview.interviewTitle = response.interviewTitle,
        this.interview.interviewDate =  response.interviewDate
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }
}
