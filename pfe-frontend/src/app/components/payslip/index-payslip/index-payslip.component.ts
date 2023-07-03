import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Payslip } from 'src/app/services/payslip/Payslip';
import { PayslipService } from 'src/app/services/payslip/payslip.service';
import Swal from 'sweetalert2';
import * as pdfMake from "pdfmake/build/pdfmake";
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { Payroll } from 'src/app/services/payroll/Payroll';
import { Payrollservice } from 'src/app/services/payroll/payroll.service';
(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-index-payslip',
  templateUrl: './index-payslip.component.html',
  styleUrls: ['./index-payslip.component.css']
})
export class IndexPayslipComponent {
  payslips: Array<Payslip> = [];
  payrollId!: number;
  payrolls: Array<Payroll> = [];
  payroll: Payroll = new Payroll();
  employeeId!: number;

  constructor(private router: Router,private route: ActivatedRoute, private payslipService: PayslipService,private payrollService: Payrollservice) {
    this.payroll.employee = {
      id: 0, firstName: "", lastName: "", email: "",
    };
  }

  ngOnInit(): void {
    this.payrollId = Number(this.route.snapshot.paramMap.get('id'));
    this.payslipService.getPayslipsByPayrollId(this.payrollId).subscribe(payslips => {
      this.payslips = payslips;
    },
    error => {
      console.log("error retrieving the payslips")
    });
    this.getPayroll();
  }


  private getPayroll() {
    this.payrollId = Number(this.route.snapshot.paramMap.get('id'));
    this.payrollService.getPayrollById(this.payrollId).subscribe({
      next: (response) => {
        this.payroll.id = response.id;
        this.employeeId = response.employee.id;
        this.payroll.employee.email = response.employee.email;
        this.payroll.employee.lastName = response.employee.lastName;
        this.payroll.employee.firstName = response.employee.firstName;
        console.log(this.employeeId);
        this.payrollService.getPayrollsByEmployeeId(this.employeeId).subscribe(payrolls => {
          this.payrolls = payrolls;
        },
        error => {
          console.log("ERROR getPayrollsByEmployeeId");
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }


  deletePayslip(payrollId: number): void {
    this.payrollId = Number(this.route.snapshot.paramMap.get('id'));
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer ce bulletin de paie ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.payslipService.deletePayslip(payrollId).subscribe({
          next: () => {
            // Success message or further actions
            Swal.fire('Supprimé!', 'le bulletin de paie a été supprimé.', 'success');

            this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
              this.router.navigate(['/payslips/payroll',this.payrollId]);
            });
          },
          error: () => {
            // Error message or handling
            Swal.fire('Erreur!', 'Une erreur s\'est produite lors de la suppression de la gestion du bulletin de paie.', 'error');
          }
        });
      }
    });
  }

  generatePDF() {
    const payslip = this.payslips[0];
    const docDefinition: any = {
      pageSize: {
        width: 595.28, // Standard A4 width in points
        height: 842, // Standard A4 height in points
      },
      content: [
        {
          text: 'Bulletin de paie',
          style: 'header',
          alignment: 'center',
          margin: [0, 20],
        },
        {
          text: 'Nom de l\'entreprise: The Briden',
          style: 'companyName',
          alignment: 'center',
          margin: [0, 10],
        },
        {
          text: 'Adresse: 123 rue Olivier de Serres',
          style: 'address',
          alignment: 'center',
          margin: [0, 5],
        },
        {
          canvas: [
            {
              type: 'line',
              x1: 0,
              y1: 5,
              x2: 595 - 2 * 40,
              y2: 5,
              lineWidth: 1,
              lineColor: '#000',
              margin: [0, 20],
            },
          ],
        },
        {
          text: 'Informations employé',
          style: 'sectionHeader',
          margin: [0, 20, 0, 10],
        },
        {
          columns: [
            {
              width: '50%',
              text: `Prénom: ${this.payroll.employee.firstName}`,
              margin: [0, 5],
            },
            {
              width: '50%',
              text: `Nom: ${this.payroll.employee.lastName}`,
              margin: [0, 5],
            },
          ],
          columnGap: 10,
          margin: [0, 5],
        },
        {
          columns: [
            {
              width: '50%',
              text: `Email: ${this.payroll.employee.email}`,
              margin: [0, 5],
            },
            {
              width: '50%',
              text: '',
            },
          ],
          columnGap: 10,
          margin: [0, 5],
        },
        {
          canvas: [
            {
              type: 'line',
              x1: 0,
              y1: 5,
              x2: 595 - 2 * 40,
              y2: 5,
              lineWidth: 1,
              lineColor: '#000',
              margin: [0, 20],
            },
          ],
        },
        {
          text: 'Détails de la paie',
          style: 'sectionHeader',
          margin: [0, 20, 0, 10],
        },
        {
          table: {
            widths: ['*', '*'],
            body: [
              [
                { text: 'Salaire de base par mois:', fillColor: '#F2F2F2', alignment: 'left' },
                { text: `${payslip.monthlyBasedSalary} DH`, alignment: 'center' },
              ],
              [
                { text: 'Heures travaillées ce mois-ci:', fillColor: '#F2F2F2', alignment: 'left' },
                { text: `${payslip.monthlyHoursWorked} heures`, alignment: 'center' },
              ],
              [
                { text: 'Paiement bonus:', fillColor: '#F2F2F2', alignment: 'left' },
                { text: `${payslip.bonusPaiment} DH`, alignment: 'center' },
              ],
              [
                { text: 'Salaire brut:', fillColor: '#F2F2F2', alignment: 'left' },
                { text: `${payslip.monthlyNetSalary} DH`, alignment: 'center' },
              ],
            ],
          },
          layout: {
            fillColor: function (i: number, node: any) {
              return i % 2 === 0 ? '#FFFFFF' : '#F9F9F9';
            },
          },
          margin: [0, 5],
        },
        {
          text: `Salaire net total: ${payslip.monthlyNetSalary} DH`,
          style: 'totalNetSalary',
          margin: [0, 20],
          alignment: 'center',
        },
      ],
      styles: {
        header: {
          fontSize: 20,
          bold: true,
        },
        companyName: {
          fontSize: 16,
          bold: true,
        },
        address: {
          fontSize: 12,
        },
        sectionHeader: {
          fontSize: 14,
          bold: true,
        },
        totalNetSalary: {
          fontSize: 16,
          bold: true,
        },
      },
    };
  
    (pdfMake as any).createPdf(docDefinition).open();
  }
  
}
