import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Payroll } from 'src/app/services/payroll/Payroll';
import { Payrollservice } from 'src/app/services/payroll/payroll.service';
import { PayslipService } from 'src/app/services/payslip/payslip.service';
import Swal from 'sweetalert2';
import { PayslipPayload } from '../../payslip/payslip-payload';
import * as pdfMake from "pdfmake/build/pdfmake";
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-view-payroll',
  templateUrl: './view-payroll.component.html',
  styleUrls: ['./view-payroll.component.css']
})
export class ViewPayrollComponent {
  payrolls: Array<Payroll> = [];
  payroll: Payroll = new Payroll();
  payrollId!: number;
  employeeId!: number;
  createPayslipForm: FormGroup = new FormGroup({});
  payslipPayload: PayslipPayload;
  isNotValid: boolean = false;
  isError: boolean = false;

  
      


  constructor(private route: ActivatedRoute, private payrollService: Payrollservice, private router: Router,private payslipService: PayslipService) {
    this.payroll.employee = {
      id: 0, firstName: "", lastName: "", email: "",
    };
    this.payslipPayload = {
      payrollId: null,
    }
  }

  ngOnInit() {
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
        this.payroll.payrollDate = response.payrollDate;
        this.payroll.monthlyNetSalary = response.monthlyNetSalary;
        this.payroll.monthlyBasedSalary = response.monthlyBasedSalary;
        this.payroll.monthlyHoursWorked = response.monthlyHoursWorked;
        this.payroll.bonusPaiment = response.bonusPaiment;

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

  generatePDF() {
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
            widths: ['auto', '*', 'auto'],
            body: [
              [
                { text: 'Salaire de base par mois:', fillColor: '#F2F2F2' },
                { text: `${this.payroll.monthlyBasedSalary} DH` },
                { text: '', fillColor: '#F2F2F2' },
              ],
              [
                { text: 'Heures travaillées ce mois-ci:', fillColor: '#F2F2F2' },
                { text: `${this.payroll.monthlyHoursWorked} heures` },
                { text: '', fillColor: '#F2F2F2' },
              ],
              [
                { text: 'Paiement bonus:', fillColor: '#F2F2F2' },
                { text: `${this.payroll.bonusPaiment} DH` },
                { text: '', fillColor: '#F2F2F2' },
              ],
              [
                { text: 'Salaire brut:', fillColor: '#F2F2F2' },
                { text: `${this.payroll.monthlyNetSalary} DH` },
                { text: '', fillColor: '#F2F2F2' },
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
          text: `Salaire net total: ${this.payroll.monthlyNetSalary} DH`,
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

  createPayslip() {
    this.payrollId = Number(this.route.snapshot.paramMap.get('id'));
    this.payslipPayload.payrollId = this.payrollId;
 
    if (this.payslipPayload.payrollId == null) {
      this.isNotValid = true;
      console.log("Empty id");
    } else {
      this.payslipService.createPayslip(this.payslipPayload).subscribe({
        next: () => {
          console.log("Ajouter !");
          this.generatePDF()
        },
        error: () => {
          this.isError = true;
          console.log("error server");
        }
      })
    }
  }

  changePayroll(payrollId: number) {
    this.router.navigate(['/payrolls', payrollId]);
    this.getPayroll();
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

            this.getPayroll();
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
