import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecruitmentDemand } from 'src/app/services/recruitment_demand/RecruitmentDemand';
import { RecruitmentDemandService } from 'src/app/services/recruitment_demand/recruitment-demand.service';

@Component({
  selector: 'app-view-recruitment-demand',
  templateUrl: './view-recruitment-demand.component.html',
  styleUrls: ['./view-recruitment-demand.component.css']
})
export class ViewRecruitmentDemandComponent {

  recruitmentDemand: RecruitmentDemand = new RecruitmentDemand();
  recruitmentDemandId!: number;

  constructor(private route:ActivatedRoute, private router: Router, private recruitmentDemandService: RecruitmentDemandService) {}

  ngOnInit() {
    this.recruitmentDemandId = Number(this.route.snapshot.paramMap.get('id'));
    this.recruitmentDemandService.getRecruitmentDemandById(this.recruitmentDemandId).subscribe({
      next: (response) => {
        this.recruitmentDemand.postTitle = response.postTitle,
        this.recruitmentDemand.postDescription =  response.postDescription,
        this.recruitmentDemand.numberOfYearsOfExperience =  response.numberOfYearsOfExperience,
        this.recruitmentDemand.numberOfProfiles =  response.numberOfProfiles,
        this.recruitmentDemand.levelOfStudies =  response.levelOfStudies,
        this.recruitmentDemand.statusOfDemand =  response.statusOfDemand,
        this.recruitmentDemand.dateOfDemand =  response.dateOfDemand
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }
}
