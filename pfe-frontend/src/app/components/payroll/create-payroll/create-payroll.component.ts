import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PayrollPayload } from '../payroll-payload';
import { ActivatedRoute, Router } from '@angular/router';
import { Payrollservice } from 'src/app/services/payroll/payroll.service';

@Component({
  selector: 'app-create-payroll',
  templateUrl: './create-payroll.component.html',
  styleUrls: ['./create-payroll.component.css']
})
export class CreatePayrollComponent {
  createPayrollForm: FormGroup = new FormGroup({});
  payrollPayload: PayrollPayload;
  isNotValid: boolean = false;
  isError: boolean = false;
  date: Date = new Date("2023-01-01");    
  employeeId!: number;

  constructor(private router: Router,private route: ActivatedRoute, private payrollService: Payrollservice) {
    this.payrollPayload = {
      payrollDate: null,
      monthlyBasedSalary: null,
      monthlyHoursWorked: null,
      bonusPaiment: null,
      employeeId: null,
    }
  }

  ngOnInit() {
    this.employeeId = Number(this.route.snapshot.paramMap.get('id'));
    this.createPayrollForm = new FormGroup({
      payrollDate: new FormControl('', Validators.required),
      monthlyHoursWorked: new FormControl('', Validators.required),
      monthlyBasedSalary: new FormControl('', Validators.required),
      bonusPaiment: new FormControl('', Validators.required),
      employeeId: new FormControl(this.employeeId, Validators.required)
    });
  }

  createPayroll() {
    this.payrollPayload.payrollDate = this.createPayrollForm.get('payrollDate')?.value;
    this.payrollPayload.monthlyHoursWorked = this.createPayrollForm.get('monthlyHoursWorked')?.value;
    this.payrollPayload.monthlyBasedSalary = this.createPayrollForm.get('monthlyBasedSalary')?.value;
    this.payrollPayload.bonusPaiment = this.createPayrollForm.get('bonusPaiment')?.value;
    this.payrollPayload.employeeId = this.createPayrollForm.get('employeeId')?.value;
 
    if (this.payrollPayload.payrollDate == null || this.payrollPayload.monthlyHoursWorked == null || this.payrollPayload.monthlyBasedSalary == null ||this.payrollPayload.bonusPaiment == null ||this.payrollPayload.employeeId == null) {
      this.isNotValid = true;
    } else {
      this.payrollService.createPayroll(this.payrollPayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/payrolls');
        },
        error: () => {
          this.isError = true;
        }
      })
    }
  }
}
