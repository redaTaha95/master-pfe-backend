import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Project } from 'src/app/services/project/Project';
import { ProjectService } from 'src/app/services/project/project.service';

@Component({
  selector: 'app-select-project',
  templateUrl: './select-project.component.html',
  styleUrls: ['./select-project.component.css']
})
export class SelectProjectComponent {
  projects: Array<Project> = [];

  constructor(private router: Router, private projectService: ProjectService) {
    this.projectService.getAllProjects().subscribe(projects => {
      this.projects = projects;
    })
  }

  ngOnInit(): void {
  }

}
