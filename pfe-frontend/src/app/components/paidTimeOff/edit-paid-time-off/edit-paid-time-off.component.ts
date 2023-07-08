import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PaidTimeOffService } from 'src/app/services/paidTimeOff/paid-time-off.service';
import { PaidTimeOffPayload } from '../payroll-payload';

@Component({
  selector: 'app-edit-paid-time-off',
  templateUrl: './edit-paid-time-off.component.html',
  styleUrls: ['./edit-paid-time-off.component.css']
})
export class EditPaidTimeOffComponent {
  editPaidTimeOffForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  paidTimeOffId!: number;

  constructor(private route: ActivatedRoute, private router: Router, private paidTimeOffService: PaidTimeOffService) {
    
    this.editPaidTimeOffForm = new FormGroup({
      startDate: new FormControl('', Validators.required),
      endDate: new FormControl('', Validators.required),
      details: new FormControl('', Validators.required),
      employeeId: new FormControl(null)
    });
  }

  ngOnInit() {
    
    this.paidTimeOffId = Number(this.route.snapshot.paramMap.get('id'));
    this.paidTimeOffService.getpaidTimeOffById(this.paidTimeOffId).subscribe({
      next: (response) => {
        this.editPaidTimeOffForm.setValue({
          
          startDate: response.startDate,
          endDate: response.endDate,
          details: response.details,
          employeeId: response.employee.id 
      
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  editpaidTimeOff() {
    if (this.editPaidTimeOffForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const paidTimeOffPayload: PaidTimeOffPayload = {
      startDate: this.editPaidTimeOffForm.value.startDate,
      endDate: this.editPaidTimeOffForm.value.endDate,
      details: this.editPaidTimeOffForm.value.details,
      employeeId: this.editPaidTimeOffForm.value.employeeId,
    };

    this.paidTimeOffService.editpaidTimeOff(paidTimeOffPayload, this.paidTimeOffId).subscribe({
      next: () => {
        this.router.navigateByUrl('/paidTimesOff');
      },
      error: () => {
        this.isError = true;
      }
    });
  }
}
