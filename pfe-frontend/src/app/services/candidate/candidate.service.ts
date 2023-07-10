import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Candidate } from './Candidate';
import { CandidatePayload } from 'src/app/components/candidate/candidate-payload';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {

  baseUrl = environment.baseURL;
  candidatesUrl = '/candidates';

  constructor(private http: HttpClient) { }
  
  getAllCandidates(): Observable<Array<Candidate>> {
    return this.http.get<Array<Candidate>>(this.baseUrl + this.candidatesUrl);
  }

  getCandidateById(id: number): Observable<Candidate> {
    return this.http.get<Candidate>(this.baseUrl + this.candidatesUrl + '/' + id);
  }

  createCandidate(candidatePayload: CandidatePayload): Observable<Candidate> {
    return this.http.post<Candidate>(this.baseUrl + this.candidatesUrl, candidatePayload);
  }

  updateCandidate(candidatePayload: CandidatePayload, candidateId: number): Observable<Candidate> {
    return this.http.put<Candidate>(this.baseUrl + this.candidatesUrl + '/' + candidateId, candidatePayload);
  }

  deleteCandidate(candidateId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + this.candidatesUrl + '/' + candidateId);
  }
}
