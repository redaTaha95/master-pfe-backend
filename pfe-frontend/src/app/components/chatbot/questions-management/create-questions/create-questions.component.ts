import { Component } from '@angular/core';
import { Question } from 'src/app/services/chatbot/Question';
import { QuestionService } from 'src/app/services/chatbot/question.service';
import { QuestionPayload } from '../question-payload';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-questions',
  templateUrl: './create-questions.component.html',
  styleUrls: ['./create-questions.component.css']
})
export class CreateQuestionsComponent {
  createQuestionForm: FormGroup = new FormGroup({});
  questionPayload: QuestionPayload;
  isNotValid: boolean = false;
  isError: boolean = false;
  search = '';
  open = false;
  questions: Array<Question> = [];
  filteredQuestions: Array<Question> = [];

  constructor(private router: Router, private questionService: QuestionService) {
    questionService.getAllQuestions().subscribe(questions => this.questions = questions);
    this.questionPayload = {
      questionText: '',
      answerText: '',
      parentQuestionId: null
    }
  }

  ngOnInit() {
    this.createQuestionForm = new FormGroup({
      questionText: new FormControl('', Validators.required),
      answerText: new FormControl('', Validators.required),
      parentQuestionId: new FormControl('')
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
  

  createQuestion() {
    this.questionPayload.questionText = this.createQuestionForm.get('questionText')?.value;
    this.questionPayload.answerText = this.createQuestionForm.get('answerText')?.value;
    this.questionPayload.parentQuestionId = this.createQuestionForm.get('parentQuestionId')?.value;

    if (this.questionPayload.questionText === '' || this.questionPayload.answerText === '') {
      this.isNotValid = true;
    } else {
      this.questionService.addQuestion(this.questionPayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/chatbot');
        },
        error: () => {
          this.isError = true;
        }
      })
    }
  }
}
