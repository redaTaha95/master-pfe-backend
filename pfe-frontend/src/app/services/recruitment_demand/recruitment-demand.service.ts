import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RecruitmentDemand } from './RecruitmentDemand';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { RecruitmentDemandPayload } from 'src/app/components/recruitment_demand/recruitment-demand-payload';

@Injectable({
  providedIn: 'root'
})
export class RecruitmentDemandService {

  baseUrl = environment.baseURL;
  recruitmentDemandUrl = '/recruitment_demands';

  constructor(private http: HttpClient) { }
  
  getAllRecruitmentDemands(): Observable<Array<RecruitmentDemand>> {
    return this.http.get<Array<RecruitmentDemand>>(this.baseUrl + this.recruitmentDemandUrl);
  }

  getRecruitmentDemandById(id: number): Observable<RecruitmentDemand> {
    return this.http.get<RecruitmentDemand>(this.baseUrl + this.recruitmentDemandUrl + '/' + id);
  }

  createRecruitmentDemand(recruitmentDemandPayload: RecruitmentDemandPayload): Observable<RecruitmentDemand> {
    return this.http.post<RecruitmentDemand>(this.baseUrl + this.recruitmentDemandUrl, recruitmentDemandPayload);
  }

  updateRecruitmentDemand(recruitmentDemandPayload: RecruitmentDemandPayload, recruitmentDemandId: number): Observable<RecruitmentDemand> {
    return this.http.put<RecruitmentDemand>(this.baseUrl + this.recruitmentDemandUrl + '/' + recruitmentDemandId, recruitmentDemandPayload);
  }

  deleteRecruitmentDemand(recruitmentDemandId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + this.recruitmentDemandUrl + '/' + recruitmentDemandId);
  }
}
