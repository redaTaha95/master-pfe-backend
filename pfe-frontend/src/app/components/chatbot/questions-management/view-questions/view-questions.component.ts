import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Question } from 'src/app/services/chatbot/Question';
import { QuestionService } from 'src/app/services/chatbot/question.service';

@Component({
  selector: 'app-view-questions',
  templateUrl: './view-questions.component.html',
  styleUrls: ['./view-questions.component.css']
})
export class ViewQuestionsComponent {
  question: Question = new Question();
  questionId!: number;

  constructor(private route: ActivatedRoute, private questionService: QuestionService, private router: Router) {}

  ngOnInit() {
    this.questionId = Number(this.route.snapshot.paramMap.get('id'));
    this.questionService.getQuestionById(this.questionId).subscribe({
      next: (response) => {
        this.question.questionText = response.questionText,
        this.question.answerText = response.answerText,
        this.question.subQuestions = response.subQuestions
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }
}
