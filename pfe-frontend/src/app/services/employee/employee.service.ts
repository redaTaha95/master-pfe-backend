import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { Employee } from './Employee';
import { Observable } from 'rxjs';
import { EmployeePayload } from 'src/app/components/employee/employee-payload';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  getAllEmployees(): Observable<Array<Employee>> {
    return this.http.get<Array<Employee>>(this.baseUrl + '/employees');
  }

  createEmployee(employeePayload: EmployeePayload): Observable<Employee> {
    return this.http.post<Employee>(this.baseUrl + '/employees', employeePayload);
  }

  getEmployeeById(id: number): Observable<Employee> {
    return this.http.get<Employee>(this.baseUrl + '/employees/' + id);
  }

  editEmployee(employeePayload: EmployeePayload, employeeId: number): Observable<Employee> {
    return this.http.put<Employee>(this.baseUrl + '/employees/' + employeeId, employeePayload);
  }

  deleteEmployee(employeeId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/employees/' + employeeId);
  }  
}
