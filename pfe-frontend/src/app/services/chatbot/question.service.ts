import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Question } from './Question';
import { QuestionPayload } from 'src/app/components/chatbot/questions-management/question-payload';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  addQuestion(question: QuestionPayload): Observable<Question> {
    return this.http.post<Question>(this.baseUrl + '/questions', question);
  }

  updateQuestion(question: QuestionPayload, questionId: number): Observable<Question> {
    return this.http.put<Question>(this.baseUrl + '/questions/' + questionId, question);
  }

  getAllQuestions(): Observable<Array<Question>> {
    return this.http.get<Array<Question>>(this.baseUrl + '/questions');
  }

  getAllParentQuestions(): Observable<Array<Question>> {
    return this.http.get<Array<Question>>(this.baseUrl + '/questions/parents');
  }

  getQuestionById(id: number): Observable<Question> {
    return this.http.get<Question>(this.baseUrl + '/questions/' + id);
  }

  deleteQuestion(questionId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/questions/' + questionId);
  }
}
