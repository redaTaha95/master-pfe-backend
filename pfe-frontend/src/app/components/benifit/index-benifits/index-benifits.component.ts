import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Benifit } from 'src/app/services/benifit/Benifit';
import { BenifitService } from 'src/app/services/benifit/benifit.service';
import { Employee } from 'src/app/services/employee/Employee';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-index-benifits',
  templateUrl: './index-benifits.component.html',
  styleUrls: ['./index-benifits.component.css']
})
export class IndexBenifitsComponent {
  employees: Array<Employee> = [];

  constructor(private router: Router, private employeeService: EmployeeService) {
    this.employeeService.getAllEmployees().subscribe(employees => {
      this.employees = employees;
    })
  }

  ngOnInit(): void {
  }
}
