import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Benifit } from './Benifit';
import { BenifitPayload } from 'src/app/components/benifit/benifit-payload';
import { TypeValidation } from './TypeValidation';
import { TypeValidationPayload } from 'src/app/components/benifit/typeValidationPayload';

@Injectable({
  providedIn: 'root'
})
export class BenifitService {
  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  getAllBenifits(): Observable<Array<Benifit>> {
    return this.http.get<Array<Benifit>>(this.baseUrl + '/benifits');
  }

  getAllTypeValidations(): Observable<Array<TypeValidation>> {
    return this.http.get<Array<TypeValidation>>(this.baseUrl + '/typeValidations');
  }

  getAllEmployeesWithLatestBenifit(): Observable<Array<Benifit>> {
    return this.http.get<Array<Benifit>>(this.baseUrl + '/benifits/employeesLatestBenifit');
  }

  createBenifit(benifitPayload: BenifitPayload): Observable<Benifit> {
    return this.http.post<Benifit>(this.baseUrl + '/benifits', benifitPayload);
  }

  createTypeValidation(typeValidationPayload: TypeValidationPayload): Observable<TypeValidation> {
    return this.http.post<TypeValidation>(this.baseUrl + '/typeValidations', typeValidationPayload);
  }

  getBenifitById(id: number): Observable<Benifit> {
    return this.http.get<Benifit>(this.baseUrl + '/benifits/' + id);
  }

  getBenifitsByEmployeeId(id: number): Observable<Array<Benifit>> {
    return this.http.get<Array<Benifit>>(this.baseUrl + '/benifits/employee/' + id);
  }

  getTypeValidationById(id: number): Observable<TypeValidation> {
    return this.http.get<TypeValidation>(this.baseUrl + '/typeValidations/' + id);
  }

  editBenifit(benifitPayload: BenifitPayload, BenifitId: number): Observable<Benifit> {
    return this.http.put<Benifit>(this.baseUrl + '/benifits/' + BenifitId, benifitPayload);
  }

  deleteBenifit(BenifitId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/benifits/' + BenifitId);
  }  
}
