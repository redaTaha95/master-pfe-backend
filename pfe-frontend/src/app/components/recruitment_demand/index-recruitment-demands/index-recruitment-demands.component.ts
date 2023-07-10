import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RecruitmentDemand } from 'src/app/services/recruitment_demand/RecruitmentDemand';
import { RecruitmentDemandService } from 'src/app/services/recruitment_demand/recruitment-demand.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-index-recruitment-demands',
  templateUrl: './index-recruitment-demands.component.html',
  styleUrls: ['./index-recruitment-demands.component.css']
})
export class IndexRecruitmentDemandsComponent {

  recruitmentDemands: Array<RecruitmentDemand> = [];

  constructor(private router: Router, private recruitmentDemandService: RecruitmentDemandService) {
    this.recruitmentDemandService.getAllRecruitmentDemands().subscribe(recruitmentDemands => {
      this.recruitmentDemands = recruitmentDemands;
    })
  }

  ngOnInit(): void {
  }

  deleteRecruitmentDemand(recruitmentDemandId: number): void {
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer cette demande de recrutement ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.recruitmentDemandService.deleteRecruitmentDemand(recruitmentDemandId).subscribe({
          next: () => {
            Swal.fire("Supprimé", 'La demande de recrutement a été supprimée avec succès', 'success');

            this.router.navigateByUrl('/', { skipLocationChange: true}).then(() => {
              this.router.navigate(['/recruitment_demands']);
            });
          },

          error: () => {
            Swal.fire('Erreur', 'Une erreur s\'est produite lors de la suppression de la demande de recrutement', 'error');
          }
        });
      }
    });
  }
}
