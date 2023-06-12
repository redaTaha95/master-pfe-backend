import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import { Employee } from 'src/app/services/employee/Employee';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-index-employees',
  templateUrl: './index-employees.component.html',
  styleUrls: ['./index-employees.component.css']
})
export class IndexEmployeesComponent {
  employees: Array<Employee> = [];

  constructor(private router: Router, private employeeService: EmployeeService) {
    this.employeeService.getAllEmployees().subscribe(employees => {
      this.employees = employees;
    })
  }

  ngOnInit(): void {
  }

  deleteEmployee(employeeId: number): void {
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer cet employé ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.employeeService.deleteEmployee(employeeId).subscribe({
          next: () => {
            // Success message or further actions
            Swal.fire('Supprimé!', 'L\'employé a été supprimé.', 'success');

            this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
              this.router.navigate(['/employees']);
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
