import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from 'src/app/services/project/Project';
import { ProjectService } from 'src/app/services/project/project.service';

@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.component.html',
  styleUrls: ['./view-project.component.css']
})
export class ViewProjectComponent {
  project: Project = new Project();
  projectId!: number;

  constructor(private route: ActivatedRoute, private projectService: ProjectService, private router: Router) {}

  ngOnInit() {
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));
    this.projectService.getProjectById(this.projectId).subscribe({
      next: (response) => {
        this.project.name = response.name,
        this.project.description = response.description,
        this.project.startDate = response.startDate,
        this.project.endDate = response.endDate
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }
}
