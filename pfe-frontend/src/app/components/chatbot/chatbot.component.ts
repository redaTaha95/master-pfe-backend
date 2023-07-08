import { Component, ElementRef, ViewChild } from '@angular/core';
import { Question } from 'src/app/services/chatbot/Question';
import { QuestionService } from 'src/app/services/chatbot/question.service';

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent {
  @ViewChild('chatContentElement') chatContentElementRef!: ElementRef;
  questions: Array<Question> = [];
  askedQuestions: Array<Question> = [];
  formHidden = true;

  constructor(private questionsService: QuestionService) {
    this.questionsService.getAllParentQuestions().subscribe(questions => this.questions = questions);
  }

  askForQuestion(questionId: number) {
    this.questionsService.getQuestionById(questionId).subscribe(question => {

      this.askedQuestions.push(question);
  
      if (question.subQuestions.length > 0) {
        this.questions = question.subQuestions;
      } else {
        this.questionsService.getAllParentQuestions().subscribe(questions => this.questions = questions);
      }
  
      // Delay the display of the answer
      if (this.askedQuestions.length > 0) {
        const lastQuestionIndex = this.askedQuestions.length - 1;
        const lastQuestion = this.askedQuestions[lastQuestionIndex];
  
        // Delay the display of the answer by 2 seconds
        setTimeout(() => {
          lastQuestion.showAnswerText = true;
        }, 2000);

        const container = this.chatContentElementRef.nativeElement;
        requestAnimationFrame(() => {
          container.scrollTop = container.scrollHeight;
        });
      }
    });
  }

  toggleChatbox() {
    this.formHidden = !this.formHidden;
  }
}
