import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ProjectPayload } from '../project-payload';
import { Router } from '@angular/router';
import { ProjectService } from 'src/app/services/project/project.service';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css']
})
export class CreateProjectComponent {
  createProjectForm: FormGroup = new FormGroup({});
  projectPayload: ProjectPayload;
  isNotValid: boolean = false;
  isError: boolean = false;

  constructor(private router: Router, private projectService: ProjectService) {
    this.projectPayload = {
      name: null,
      description: null,
      startDate: null,
      endDate: null,
    }
  }

  ngOnInit() {
    this.createProjectForm = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      startDate: new FormControl('', Validators.required),
      endDate: new FormControl('', Validators.required)
    });
  }

  createProject() {
    this.projectPayload.name = this.createProjectForm.get('name')?.value;
    this.projectPayload.description = this.createProjectForm.get('description')?.value;
    this.projectPayload.startDate = this.createProjectForm.get('startDate')?.value;
    this.projectPayload.endDate = this.createProjectForm.get('endDate')?.value;

    if (this.projectPayload.name === '' || this.projectPayload.description === '' || this.projectPayload.startDate === null || this.projectPayload.endDate === null) {
      this.isNotValid = true;
    } else {
      this.projectService.createProject(this.projectPayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/projects');
        },
        error: () => {
          this.isError = true;
        }
      })
    }
  }
}
