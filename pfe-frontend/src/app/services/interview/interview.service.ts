import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Interview } from './Interview';
import { InterviewPayload } from 'src/app/components/interview/interview-payload';

@Injectable({
  providedIn: 'root'
})
export class InterviewService {

  baseUrl = environment.baseURL;
  interviewsUrl = '/interviews';

  constructor(private http: HttpClient) { }
  
  getAllInterviews(): Observable<Array<Interview>> {
    return this.http.get<Array<Interview>>(this.baseUrl + this.interviewsUrl);
  }

  getInterviewById(id: number): Observable<Interview> {
    return this.http.get<Interview>(this.baseUrl + this.interviewsUrl + '/' + id);
  }

  createInterview(interviewPayload: InterviewPayload): Observable<Interview> {
    return this.http.post<Interview>(this.baseUrl + this.interviewsUrl, interviewPayload);
  }

  updateInterview(interviewPayload: InterviewPayload, interviewId: number): Observable<Interview> {
    return this.http.put<Interview>(this.baseUrl + this.interviewsUrl + '/' + interviewId, interviewPayload);
  }

  deleteInterview(interviewId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + this.interviewsUrl + '/' + interviewId);
  }
}
