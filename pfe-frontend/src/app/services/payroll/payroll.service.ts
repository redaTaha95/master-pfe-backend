import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Observable } from 'rxjs';
import { Payroll } from './Payroll';
import { PayrollPayload } from 'src/app/components/payroll/payroll-payload';

@Injectable({
  providedIn: 'root'
})
export class Payrollservice {
  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  getAllPayrolls(): Observable<Array<Payroll>> {
    return this.http.get<Array<Payroll>>(this.baseUrl + '/payrolls');
  }

  getAllEmployeesWithLatestPayroll(): Observable<Array<Payroll>> {
    return this.http.get<Array<Payroll>>(this.baseUrl + '/payrolls/employeesLatestPayroll');
  }

  createPayroll(PayrollPayload: PayrollPayload): Observable<Payroll> {
    return this.http.post<Payroll>(this.baseUrl + '/payrolls', PayrollPayload);
  }

  getPayrollById(id: number): Observable<Payroll> {
    return this.http.get<Payroll>(this.baseUrl + '/payrolls/' + id);
  }

  getPayrollsByEmployeeId(id: number): Observable<Array<Payroll>> {
    return this.http.get<Array<Payroll>>(this.baseUrl + '/payrolls/employee/' + id);
  }

  editPayroll(PayrollPayload: PayrollPayload, PayrollId: number): Observable<Payroll> {
    return this.http.put<Payroll>(this.baseUrl + '/payrolls/' + PayrollId, PayrollPayload);
  }

  deletePayroll(PayrollId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/payrolls/' + PayrollId);
  }  
}
