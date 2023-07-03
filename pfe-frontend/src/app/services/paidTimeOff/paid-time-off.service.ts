import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

import { PaidTimeOffPayload } from 'src/app/components/paidTimeOff/payroll-payload';
import { PaidTimeOff } from './PaidTimeOff';



@Injectable({
  providedIn: 'root'
})
export class PaidTimeOffService {

  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  getAllpaidTimeOffs(): Observable<Array<PaidTimeOff>> {
    return this.http.get<Array<PaidTimeOff>>(this.baseUrl + '/paidTimesOff');
  }

  createpaidTimeOff(paidTimeOffPayload: PaidTimeOffPayload): Observable<PaidTimeOff> {
    return this.http.post<PaidTimeOff>(this.baseUrl + '/paidTimesOff', paidTimeOffPayload);
  }

  getpaidTimeOffById(id: number): Observable<PaidTimeOff> {
    return this.http.get<PaidTimeOff>(this.baseUrl + '/paidTimesOff/' + id);
  }

  getpaidTimesOffByEmployeeId(id: number): Observable<Array<PaidTimeOff>> {
    return this.http.get<Array<PaidTimeOff>>(this.baseUrl + '/paidTimesOff/employee/' + id);
  }

  getLatestpaidTimesOff(): Observable<Array<PaidTimeOff>> {
    return this.http.get<Array<PaidTimeOff>>(this.baseUrl + '/paidTimesOff/employeesLatestPaidTimeOff');
  }

  editpaidTimeOff(paidTimeOffPayload: PaidTimeOffPayload, paidTimeOffId: number): Observable<PaidTimeOff> {
    return this.http.put<PaidTimeOff>(this.baseUrl + '/paidTimesOff/' + paidTimeOffId, paidTimeOffPayload);
  }

  deletepaidTimeOff(paidTimeOffId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/paidTimesOff/' + paidTimeOffId);
  }  
}
