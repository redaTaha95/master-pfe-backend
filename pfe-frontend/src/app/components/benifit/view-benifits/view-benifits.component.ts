import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Benifit } from 'src/app/services/benifit/Benifit';
import { BenifitService } from 'src/app/services/benifit/benifit.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-benifits',
  templateUrl: './view-benifits.component.html',
  styleUrls: ['./view-benifits.component.css']
})
export class ViewBenifitsComponent {
  benifits: Array<Benifit>= [];
  benifit: Benifit = new Benifit();
  benifitId!: number;
  employeeId!: number;
 
  constructor(private route: ActivatedRoute, private benifitService: BenifitService, private router: Router) {
    this.benifit.employee = {
      id: 0, firstName: "", lastName: "", email: "",
    };
    this.benifit.typeValidation = {
      id: 0, type: "", matricule: true,
    };
  }

  ngOnInit() {
  this.getBenifits();
  }

  getBenifits(){
    this.employeeId = Number(this.route.snapshot.paramMap.get('id'));
        this.benifitService.getBenifitsByEmployeeId(this.employeeId).subscribe(benifits => {
          console.log(benifits);
          this.benifits = benifits;
        },
        error => {
          console.log("ERROR getBenifitsByEmployeeId");
        });
  }

  changeBenifit(benifitId: number) {
    this.router.navigate(['/benifits', benifitId]);
    this.getBenifits();
  }


  deletebenifit(benifitId: number): void {
    this.benifitId = Number(this.route.snapshot.paramMap.get('id'));
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer cet avantage social pour cet employée ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.benifitService.deleteBenifit(benifitId).subscribe({
          next: () => {
            // Success message or further actions
            Swal.fire('Supprimé!', 'L\'avantage social pour cet employée a été supprimé.', 'success');

            this.getBenifits();
          },
          error: () => {
            // Error message or handling
            Swal.fire('Erreur!', 'Une erreur s\'est produite lors de la suppression de l\'avantage social.', 'error');
          }
        });
      }
    });
  }
}
