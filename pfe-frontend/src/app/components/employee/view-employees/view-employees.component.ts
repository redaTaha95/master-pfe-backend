import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from 'src/app/services/employee/Employee';
import { EmployeeService } from 'src/app/services/employee/employee.service';

@Component({
  selector: 'app-view-employees',
  templateUrl: './view-employees.component.html',
  styleUrls: ['./view-employees.component.css']
})
export class ViewEmployeesComponent {
  employee: Employee = new Employee();
  employeeId!: number;

  constructor(private route: ActivatedRoute, private employeeService: EmployeeService, private router: Router) {}

  ngOnInit() {
    this.employeeId = Number(this.route.snapshot.paramMap.get('id'));
    this.employeeService.getEmployeeById(this.employeeId).subscribe({
      next: (response) => {
        this.employee.email = response.email,
        this.employee.lastName = response.lastName,
        this.employee.firstName = response.firstName
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }
}
