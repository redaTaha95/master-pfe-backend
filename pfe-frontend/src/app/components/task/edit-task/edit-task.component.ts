import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from 'src/app/services/task/task.service';
import { TaskPayload } from '../task-payload';
import { ProjectService } from 'src/app/services/project/project.service';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import { Employee } from 'src/app/services/employee/Employee';
import { Project } from 'src/app/services/project/Project';
import { EmployeePayload } from '../../employee/employee-payload';
import { ProjectPayload } from '../../project/project-payload';
import { Location } from '@angular/common';

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent {
  editTaskForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  taskId!: number;
  employees: Array<Employee> = [];
  projects: Array<Project> = [];
  projectId!: number;
  employeeId!: number;
  employeePayload: EmployeePayload;
  projectPayload: ProjectPayload;

  constructor(private route: ActivatedRoute, private router: Router, private taskService: TaskService,private projectService: ProjectService,private employeeService: EmployeeService,private location: Location) {
    this.employeePayload = {
      firstName: '',
      lastName: '',
      email: '',
    };
    this.loadEmployees();
    this.projectPayload = {
      name: null,
      description: null,
      startDate: null,
      endDate: null,
    };
    this.loadProjects();
    this.editTaskForm = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      projectId: new FormControl('', Validators.required),
      status: new FormControl('', Validators.required),
      employeeId: new FormControl('', Validators.required),
      startDate: new FormControl('', Validators.required),
      endDate: new FormControl('', Validators.required)
    });
  }

  ngOnInit() {
    
    this.taskId = Number(this.route.snapshot.paramMap.get('id'));
    this.taskService.getTaskById(this.taskId).subscribe({
      next: (response) => {
        this.editTaskForm.setValue({
          name: response.name,
          description: response.description,
          projectId: response.projectId,
          status: response.status,
          employeeId: response.employee.id,
          startDate: response.startDate,
          endDate: response.endDate,
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  goBack(): void {
    this.location.back();
  }

  loadEmployees() {
    this.employeeService.getAllEmployees().subscribe(employees => {
      this.employees = employees;
      console.log(this.employees);
    },
    error => {
      console.log("ERROR getAllemployees");
    });
  }

  loadProjects() {
    this.projectService.getAllProjects().subscribe(projects => {
      this.projects = projects;
      console.log(this.projects);
    },
    error => {
      console.log("ERROR getAllProjects");
    });
  }

  editTask() {
    if (this.editTaskForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const taskPayload: TaskPayload = {
      name: this.editTaskForm.value.name,
      description: this.editTaskForm.value.description,
      projectId: this.editTaskForm.value.projectId,
      status: this.editTaskForm.value.status,
      employeeId: this.editTaskForm.value.employeeId,
      startDate: this.editTaskForm.value.startDate,
      endDate: this.editTaskForm.value.endDate,
    };

    this.taskService.editTask(this.taskId,taskPayload).subscribe({
      next: () => {
        const projectId = this.editTaskForm.value.projectId;
        const urlTree = this.router.createUrlTree(['/tasks/project', projectId]);
      this.router.navigateByUrl(urlTree);
      },
      error: () => {
        this.isError = true;
      }
    });
  }
}
