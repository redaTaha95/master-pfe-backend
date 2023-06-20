import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Question } from 'src/app/services/chatbot/Question';
import { QuestionService } from 'src/app/services/chatbot/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-index-questions',
  templateUrl: './index-questions.component.html',
  styleUrls: ['./index-questions.component.css']
})
export class IndexQuestionsComponent {
  questions: Array<Question> = [];

  constructor(private router: Router, private questionsService: QuestionService) {
    this.questionsService.getAllQuestions().subscribe(questions => this.questions = questions);
  }

  ngOnInit(): void {

  }

  deleteQuestion(questionId: number): void {
    Swal.fire({
      title: 'Confirmation',
      text: 'Souhaitez-vous réellement supprimer cette question ? Toutes les sous-questions seront également supprimées en conséquence.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.questionsService.deleteQuestion(questionId).subscribe({
          next: () => {
            // Success message or further actions
            Swal.fire('Supprimé!', 'La question a été supprimée.', 'success');

            this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
              this.router.navigate(['/chatbot']);
            });
          },
          error: () => {
            // Error message or handling
            Swal.fire('Erreur!', 'Une erreur s\'est produite lors de la suppression de la question.', 'error');
          }
        });
      }
    });
  }
}
