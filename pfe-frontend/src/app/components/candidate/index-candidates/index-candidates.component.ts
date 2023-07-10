import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Candidate } from 'src/app/services/candidate/Candidate';
import { CandidateService } from 'src/app/services/candidate/candidate.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-index-candidates',
  templateUrl: './index-candidates.component.html',
  styleUrls: ['./index-candidates.component.css']
})
export class IndexCandidatesComponent {

  candidates: Array<Candidate> = [];

  constructor(private router: Router, private candidateService: CandidateService) {
    this.candidateService.getAllCandidates().subscribe(candidates => {
      this.candidates = candidates;
    })
  }

  ngOnInit(): void {
  }

  deleteCandidate(candidateId: number): void {
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer ce candidat ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.candidateService.deleteCandidate(candidateId).subscribe({
          next: () => {
            Swal.fire("Supprimé", 'Le candidat a été supprimé avec succès', 'success');

            this.router.navigateByUrl('/', { skipLocationChange: true}).then(() => {
              this.router.navigate(['/candidates']);
            });
          },

          error: () => {
            Swal.fire('Erreur', 'Une erreur s\'est produite lors de la suppression du candidat', 'error');
          }
        });
      }
    });
  }
}
