import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EmployeePayload } from '../employee-payload';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-employees',
  templateUrl: './create-employees.component.html',
  styleUrls: ['./create-employees.component.css']
})
export class CreateEmployeesComponent {
  createEmployeeForm: FormGroup = new FormGroup({});
  employeePayload: EmployeePayload;
  isNotValid: boolean = false;
  isError: boolean = false;

  constructor(private router: Router, private employeeService: EmployeeService) {
    this.employeePayload = {
      firstName: '',
      lastName: '',
      email: ''
    }
  }

  ngOnInit() {
    this.createEmployeeForm = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required)
    });
  }

  createEmployee() {
    this.employeePayload.firstName = this.createEmployeeForm.get('firstName')?.value;
    this.employeePayload.lastName = this.createEmployeeForm.get('lastName')?.value;
    this.employeePayload.email = this.createEmployeeForm.get('email')?.value;

    if (this.employeePayload.firstName === '' || this.employeePayload.lastName === '' || this.employeePayload.email === '') {
      this.isNotValid = true;
    } else {
      this.employeeService.createEmployee(this.employeePayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/employees');
        },
        error: () => {
          this.isError = true;
        }
      })
    }
  }
}
