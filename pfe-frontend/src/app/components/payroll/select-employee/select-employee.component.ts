import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Employee } from 'src/app/services/employee/Employee';
import { EmployeeService } from 'src/app/services/employee/employee.service';

@Component({
  selector: 'app-select-employee',
  templateUrl: './select-employee.component.html',
  styleUrls: ['./select-employee.component.css']
})
export class SelectEmployeeComponent {
  employees: Array<Employee> = [];

  constructor(private router: Router, private employeeService: EmployeeService) {
    this.employeeService.getAllEmployees().subscribe(employees => {
      this.employees = employees;
    })
  }

  ngOnInit(): void {
  }


}
