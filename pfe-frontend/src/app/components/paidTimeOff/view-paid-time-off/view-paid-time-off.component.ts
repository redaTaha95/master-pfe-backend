import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PaidTimeOff } from 'src/app/services/paidTimeOff/PaidTimeOff';
import { PaidTimeOffService } from 'src/app/services/paidTimeOff/paid-time-off.service';
import { PaidTimeOffPayload } from '../payroll-payload';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-paid-time-off',
  templateUrl: './view-paid-time-off.component.html',
  styleUrls: ['./view-paid-time-off.component.css']
})
export class ViewPaidTimeOffComponent {
  paidTimeOff: PaidTimeOff = new PaidTimeOff();
  paidTimeOffId!: number;
  employeeId!: number;
  paidTimesOff: Array<PaidTimeOff> = [];
  constructor(private route: ActivatedRoute, private paidTimeOffService: PaidTimeOffService, private router: Router) {
    this.paidTimeOff.employee = {
      id: 0, firstName: "", lastName: "", email: "",
    };
  }

  ngOnInit() {
  this.getPaidTimeOff();
  }

  getPaidTimeOff(){
    this.paidTimeOffId = Number(this.route.snapshot.paramMap.get('id'));
    this.paidTimeOffService.getpaidTimeOffById(this.paidTimeOffId).subscribe({
      next: (response) => {
        this.paidTimeOff.startDate = response.startDate,
        this.paidTimeOff.endDate = response.endDate,
        this.paidTimeOff.details = response.details,
        this.employeeId = response.employee.id;
        this.paidTimeOff.employee.email = response.employee.email;
        this.paidTimeOff.employee.lastName = response.employee.lastName;
        this.paidTimeOff.employee.firstName = response.employee.firstName;
        this.paidTimeOffService.getpaidTimesOffByEmployeeId(this.employeeId).subscribe(paidTimesOff => {
          this.paidTimesOff = paidTimesOff;
        },
        error => {
          console.log("ERROR getpaidTimesOffByEmployeeId");
        });

      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  changePaidTimeOff(paidTimeOffId: number) {
    this.router.navigate(['/paidTimesOff', paidTimeOffId]);
    this.getPaidTimeOff();
  }


  deletepaidTimeOff(paidTimeOffId: number): void {
    this.paidTimeOffId = Number(this.route.snapshot.paramMap.get('id'));
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer ce congé payé?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.paidTimeOffService.deletepaidTimeOff(paidTimeOffId).subscribe({
          next: () => {
            // Success message or further actions
            Swal.fire('Supprimé!', 'Le congé payé a été supprimé.', 'success');

            this.getPaidTimeOff();
          },
          error: () => {
            // Error message or handling
            Swal.fire('Erreur!', 'Une erreur s\'est produite lors de la suppression du congé payé.', 'error');
          }
        });
      }
    });
  }
}
