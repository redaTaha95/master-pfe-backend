import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TaskPayload } from '../task-payload';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from 'src/app/services/task/task.service';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import { Employee } from 'src/app/services/employee/Employee';
import { Location } from '@angular/common';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.css']
})
export class CreateTaskComponent {
  createTaskForm: FormGroup = new FormGroup({});
  
  taskPayload: TaskPayload;
  isNotValid: boolean = false;
  isError: boolean = false;
  date: Date = new Date("2023-01-01");    
  projectId!: number;
  employeeId!: number;
  status!: string;
  employees: Array<Employee> = [];

  constructor(private router: Router,private route: ActivatedRoute, private taskService: TaskService,private employeeService: EmployeeService,private location: Location) {
    this.taskPayload = {
      name: null,
      description: null,
      projectId:null,
      status: '',
      employeeId: null,
      startDate: null,
      endDate: null,
    },

    this.loadEmployees();
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));
    this.status = String(this.route.snapshot.paramMap.get('status'));
    
  }

  ngOnInit() {
    this.createTaskForm = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      employeeId: new FormControl(null, Validators.required),
      status: new FormControl(this.status, Validators.required),
      startDate: new FormControl('', Validators.required),
      endDate: new FormControl('', Validators.required),
    });
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

  goBack(): void {
    this.location.back();
  }

  createTask() {

    this.taskPayload.name = this.createTaskForm.get('name')?.value;
    this.taskPayload.description = this.createTaskForm.get('description')?.value;
    this.taskPayload.status = this.status;
    this.taskPayload.projectId = this.projectId;
    this.taskPayload.employeeId = this.createTaskForm.get('employeeId')?.value;
    this.taskPayload.startDate = this.createTaskForm.get('startDate')?.value;
    this.taskPayload.endDate = this.createTaskForm.get('endDate')?.value;
 
    if (this.taskPayload.startDate == null || this.taskPayload.endDate == null || this.taskPayload.description == null ) {
      this.isNotValid = true;
    } else {
      this.taskService.createTask(this.taskPayload).subscribe({
        next: () => {
          const urlTree = this.router.createUrlTree(['/tasks/project', this.projectId]);
        this.router.navigateByUrl(urlTree);
        },
        error: () => {
          this.isError = true;
        }
      })
    }
  }
}
