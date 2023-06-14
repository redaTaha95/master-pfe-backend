import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import { EmployeePayload } from '../employee-payload';

@Component({
  selector: 'app-edit-employees',
  templateUrl: './edit-employees.component.html',
  styleUrls: ['./edit-employees.component.css']
})
export class EditEmployeesComponent {
  editEmployeeForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  employeeId!: number;

  constructor(private route: ActivatedRoute, private router: Router, private employeeService: EmployeeService) {
    this.editEmployeeForm = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required)
    });
  }

  ngOnInit() {
    this.employeeId = Number(this.route.snapshot.paramMap.get('id'));
    this.employeeService.getEmployeeById(this.employeeId).subscribe({
      next: (response) => {
        // Populate the form fields with the retrieved employee data
        this.editEmployeeForm.setValue({
          email: response.email,
          lastName: response.lastName,
          firstName: response.firstName
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  editEmployee() {
    if (this.editEmployeeForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const employeePayload: EmployeePayload = {
      email: this.editEmployeeForm.value.email,
      lastName: this.editEmployeeForm.value.lastName,
      firstName: this.editEmployeeForm.value.firstName
    };

    this.employeeService.editEmployee(employeePayload, this.employeeId).subscribe({
      next: () => {
        this.router.navigateByUrl('/employees');
      },
      error: () => {
        this.isError = true;
      }
    });
  }
}
