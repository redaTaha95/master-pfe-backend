import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Candidate } from 'src/app/services/candidate/Candidate';
import { CandidateService } from 'src/app/services/candidate/candidate.service';

@Component({
  selector: 'app-view-candidate',
  templateUrl: './view-candidate.component.html',
  styleUrls: ['./view-candidate.component.css']
})
export class ViewCandidateComponent {

  candidate: Candidate = new Candidate();
  candidateId!: number;

  constructor(private route:ActivatedRoute, private router: Router, private candidateService: CandidateService) {}

  ngOnInit() {
    this.candidateId = Number(this.route.snapshot.paramMap.get('id'));
    this.candidateService.getCandidateById(this.candidateId).subscribe({
      next: (response) => {
        this.candidate.firstName = response.firstName,
        this.candidate.lastName =  response.lastName,
        this.candidate.email =  response.email,
        this.candidate.phone =  response.phone,
        this.candidate.address =  response.address,
        this.candidate.sector =  response.sector,
        this.candidate.numberOfYearsOfExperience =  response.numberOfYearsOfExperience,
        this.candidate.levelOfStudies =  response.levelOfStudies
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }
}
