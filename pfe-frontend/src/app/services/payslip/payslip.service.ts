import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Payslip } from './Payslip';
import { PayslipPayload } from 'src/app/components/payslip/payslip-payload';

@Injectable({
  providedIn: 'root'
})
export class PayslipService {
  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  getAllPayslips(): Observable<Array<Payslip>> {
    return this.http.get<Array<Payslip>>(this.baseUrl + '/payslips');
  }

  createPayslip(PayslipPayload: PayslipPayload): Observable<Payslip> {
    return this.http.post<Payslip>(this.baseUrl + '/payslips', PayslipPayload);
  }

  getPayslipById(id: number): Observable<Payslip> {
    return this.http.get<Payslip>(this.baseUrl + '/payslips/' + id);
  }

  getPayslipsByPayrollId(id: number): Observable<Array<Payslip>> {
    return this.http.get<Array<Payslip>>(this.baseUrl + '/payslips/payroll/' + id);
  }

  editPayslip(PayslipPayload: PayslipPayload, PayslipId: number): Observable<Payslip> {
    return this.http.put<Payslip>(this.baseUrl + '/payslips/' + PayslipId, PayslipPayload);
  }

  deletePayslip(PayslipId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/payslips/' + PayslipId);
  }  
}
