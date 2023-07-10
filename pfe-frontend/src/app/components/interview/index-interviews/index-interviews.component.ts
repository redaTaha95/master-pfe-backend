import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Interview } from 'src/app/services/interview/Interview';
import { InterviewService } from 'src/app/services/interview/interview.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-index-interviews',
  templateUrl: './index-interviews.component.html',
  styleUrls: ['./index-interviews.component.css']
})
export class IndexInterviewsComponent {

  interviews: Array<Interview> = [];

  constructor(private router: Router, private interviewService: InterviewService) {
    this.interviewService.getAllInterviews().subscribe(interviews => {
      this.interviews = interviews;
    })
  }

  ngOnInit(): void {
  }

  deleteInterview(interviewId: number): void {
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer cet entretien ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.interviewService.deleteInterview(interviewId).subscribe({
          next: () => {
            Swal.fire("Supprimé", 'L\'entretien a été supprimé avec succès', 'success');

            this.router.navigateByUrl('/', { skipLocationChange: true}).then(() => {
              this.router.navigate(['/interviews']);
            });
          },

          error: () => {
            Swal.fire('Erreur', 'Une erreur s\'est produite lors de la suppression de l\'entretien', 'error');
          }
        });
      }
    });
  }
}
