import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from 'src/app/services/project/project.service';
import { ProjectPayload } from '../project-payload';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent {
  editProjectForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  projectId!: number;

  constructor(private route: ActivatedRoute, private router: Router, private projectService: ProjectService) {
    
    this.editProjectForm = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      startDate: new FormControl('', Validators.required),
      endDate: new FormControl('', Validators.required)
    });
  }

  ngOnInit() {
    
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));
    this.projectService.getProjectById(this.projectId).subscribe({
      next: (response) => {
        this.editProjectForm.setValue({
          name: response.name,
          description: response.description,
          startDate: response.startDate,
          endDate: response.endDate,
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  editproject() {
    if (this.editProjectForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const projectPayload: ProjectPayload = {
      name: this.editProjectForm.value.name,
      description: this.editProjectForm.value.description,
      startDate: this.editProjectForm.value.startDate,
      endDate: this.editProjectForm.value.endDate,
    };

    this.projectService.editProject(projectPayload, this.projectId).subscribe({
      next: () => {
        this.router.navigateByUrl('/paidTimesOff');
      },
      error: () => {
        this.isError = true;
      }
    });
  }
}
