import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Payrollservice } from 'src/app/services/payroll/payroll.service';
import { PayrollPayload } from '../payroll-payload';

@Component({
  selector: 'app-edit-payroll',
  templateUrl: './edit-payroll.component.html',
  styleUrls: ['./edit-payroll.component.css']
})
export class EditPayrollComponent {
  editPayrollForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  payrollId!: number;

  constructor(private route: ActivatedRoute, private router: Router, private payrollService: Payrollservice) {
    this.editPayrollForm = new FormGroup({
      payrollDate: new FormControl('', Validators.required),
      monthlyHoursWorked: new FormControl('', Validators.required),
      monthlyBasedSalary: new FormControl('', Validators.required),
      bonusPaiment: new FormControl('', Validators.required),
      employeeId: new FormControl(null)
    });
  }

  ngOnInit() {
    
    this.payrollId = Number(this.route.snapshot.paramMap.get('id'));
    this.payrollService.getPayrollById(this.payrollId).subscribe({
      next: (response) => {
        this.editPayrollForm.setValue({
          payrollDate: response.payrollDate,
          monthlyHoursWorked: response.monthlyHoursWorked,
          monthlyBasedSalary: response.monthlyBasedSalary,
          bonusPaiment: response.bonusPaiment,
          employeeId: response.employee.id,
         
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  editPayroll() {
    if (this.editPayrollForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const payrollPayload: PayrollPayload = {
      payrollDate: this.editPayrollForm.value.payrollDate,
      monthlyHoursWorked: this.editPayrollForm.value.monthlyHoursWorked,
      monthlyBasedSalary: this.editPayrollForm.value.monthlyBasedSalary,
      bonusPaiment: this.editPayrollForm.value.bonusPaiment,
      employeeId: this.editPayrollForm.value.employeeId,

    };

    this.payrollService.editPayroll(payrollPayload, this.payrollId).subscribe({
      next: () => {
        this.router.navigateByUrl('/payrolls');
      },
      error: () => {
        this.isError = true;
      }
    });
  }
}
