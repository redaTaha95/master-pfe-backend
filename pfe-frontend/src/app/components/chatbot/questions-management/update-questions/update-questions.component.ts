import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from 'src/app/services/chatbot/question.service';
import { QuestionPayload } from '../question-payload';
import { Question } from 'src/app/services/chatbot/Question';

@Component({
  selector: 'app-update-questions',
  templateUrl: './update-questions.component.html',
  styleUrls: ['./update-questions.component.css']
})
export class UpdateQuestionsComponent {
  editQuestionForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  questionId!: number;
  search = '';
  open = false;
  questions: Array<Question> = [];
  filteredQuestions: Array<Question> = [];

  constructor(private route: ActivatedRoute, private router: Router, private questionService: QuestionService) {
    questionService.getAllQuestions().subscribe(questions => this.questions = questions);
    this.editQuestionForm = new FormGroup({
      questionText: new FormControl('', Validators.required),
      answerText: new FormControl('', Validators.required),
      parentQuestionId: new FormControl('')
    });
  }

  ngOnInit() {
    this.questionId = Number(this.route.snapshot.paramMap.get('id'));
    this.questionService.getQuestionById(this.questionId).subscribe({
      next: (response) => {
        // Populate the form fields with the retrieved employee data
        this.editQuestionForm.setValue({
          questionText: response.questionText,
          answerText: response.answerText,
          parentQuestionId: response.parentQuestionId
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  editQuestion() {
    if (this.editQuestionForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const questionPayload: QuestionPayload = {
      questionText: this.editQuestionForm.value.questionText,
      answerText: this.editQuestionForm.value.answerText,
      parentQuestionId: this.editQuestionForm.value.parentQuestionId
    };

    this.questionService.updateQuestion(questionPayload, this.questionId).subscribe({
      next: () => {
        this.router.navigateByUrl('/chatbot');
      },
      error: () => {
        this.isError = true;
      }
    });
  }

  toggleOpen() {
    this.open = !this.open;
    this.updateFilteredItems(this.search);
  }

  updateFilteredItems(value: string) {
    this.search = value || '';
    this.filteredQuestions = this.questions.filter(i => i.questionText.toLowerCase().includes(this.search.toLowerCase()));
  }
  

  handleInputChange(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target instanceof HTMLInputElement) {
      const value = target.value;
      // Perform any necessary actions with the value
      this.updateFilteredItems(value);
    }
  }
}
