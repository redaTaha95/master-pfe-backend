import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PaidTimeOffService } from 'src/app/services/paidTimeOff/paid-time-off.service';
import { PaidTimeOffPayload } from '../payroll-payload';

@Component({
  selector: 'app-create-paid-time-off',
  templateUrl: './create-paid-time-off.component.html',
  styleUrls: ['./create-paid-time-off.component.css']
})
export class CreatePaidTimeOffComponent {
  createPaidTimeOffForm: FormGroup = new FormGroup({});
  paidTimeOffPayload: PaidTimeOffPayload;
  isNotValid: boolean = false;
  isError: boolean = false;
  date: Date = new Date("2023-01-01");    
  employeeId!: number;

  constructor(private router: Router,private route: ActivatedRoute, private paidTimeOffService: PaidTimeOffService) {
    this.paidTimeOffPayload = {
      startDate: null,
      endDate: null,
      details: null,
      employeeId: null,
    }
  }

  ngOnInit() {
    this.employeeId = Number(this.route.snapshot.paramMap.get('id'));
    this.createPaidTimeOffForm = new FormGroup({
      startDate: new FormControl('', Validators.required),
      endDate: new FormControl('', Validators.required),
      details: new FormControl('', Validators.required),
      employeeId: new FormControl(this.employeeId, Validators.required)
    });
  }

  createPaidTimeOff() {
    this.paidTimeOffPayload.startDate = this.createPaidTimeOffForm.get('startDate')?.value;
    this.paidTimeOffPayload.endDate = this.createPaidTimeOffForm.get('endDate')?.value;
    this.paidTimeOffPayload.details = this.createPaidTimeOffForm.get('details')?.value;
    this.paidTimeOffPayload.employeeId = this.createPaidTimeOffForm.get('employeeId')?.value;
 
    if (this.paidTimeOffPayload.startDate == null || this.paidTimeOffPayload.endDate == null || this.paidTimeOffPayload.details == null ) {
      this.isNotValid = true;
    } else {
      this.paidTimeOffService.createpaidTimeOff(this.paidTimeOffPayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/paidTimesOff');
        },
        error: () => {
          this.isError = true;
        }
      })
    }
  }
}
