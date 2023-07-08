import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Payroll } from 'src/app/services/payroll/Payroll';
import { Payrollservice } from 'src/app/services/payroll/payroll.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-index-payrolls',
  templateUrl: './index-payrolls.component.html',
  styleUrls: ['./index-payrolls.component.css']
})
export class IndexPayrollsComponent {
  payrolls: Array<Payroll> = [];


  constructor(private router: Router, private payrollService: Payrollservice) {
    this.payrollService.getAllEmployeesWithLatestPayroll().subscribe(payrolls => {
      this.payrolls = payrolls;
    })
  }

  ngOnInit(): void {
  }

  deletePayroll(employeeId: number): void {
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer cette paie?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.payrollService.deletePayroll(employeeId).subscribe({
          next: () => {
            // Success message or further actions
            Swal.fire('Supprimé!', 'la gestion de la paie a été supprimé.', 'success');

            this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
              this.router.navigate(['/payrolls']);
            });
          },
          error: () => {
            // Error message or handling
            Swal.fire('Erreur!', 'Une erreur s\'est produite lors de la suppression de la gestion de la paie.', 'error');
          }
        });
      }
    });
  }
}
