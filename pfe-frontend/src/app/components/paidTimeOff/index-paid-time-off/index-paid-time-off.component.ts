import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PaidTimeOffService } from 'src/app/services/paidTimeOff/paid-time-off.service';
import { PaidTimeOff } from 'src/app/services/paidTimeOff/PaidTimeOff';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-index-paid-time-off',
  templateUrl: './index-paid-time-off.component.html',
  styleUrls: ['./index-paid-time-off.component.css']
})
export class IndexPaidTimeOffComponent {
  paidTimeOffs: Array<PaidTimeOff> = [];

  constructor(private router: Router, private paidTimeOffService: PaidTimeOffService) {
    this.paidTimeOffService.getLatestpaidTimesOff().subscribe(paidTimeOffs => {
      this.paidTimeOffs = paidTimeOffs;
    })
  }

  ngOnInit(): void {
  }

  deletepaidTimeOff(paidTimeOffId: number): void {
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

            this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
              this.router.navigate(['/paidTimeOffs']);
            });
          },
          error: () => {
            // Error message or handling
            Swal.fire('Erreur!', 'Une erreur s\'est produite lors de la suppression de l\'employé.', 'error');
          }
        });
      }
    });
  }
}
