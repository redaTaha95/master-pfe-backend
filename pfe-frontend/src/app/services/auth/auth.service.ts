import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LocalStorageService} from "ngx-webstorage";
import { environment } from 'src/environments/environment.development';
import { LoginRequestPayload } from 'src/app/core/login/login-request.payload';
import { Observable, catchError, map, of, tap } from 'rxjs';
import { LoginResponse } from 'src/app/core/login/login-response.payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  baseUrl = environment.baseURL;

  constructor(private http: HttpClient, private localStorage: LocalStorageService) { }

  login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
    return this.http.post<LoginResponse>(this.baseUrl + '/auth/login', loginRequestPayload)
      .pipe(map(data =>{
        this.localStorage.store('userId', data.userId);
        this.localStorage.store('username', data.username);
        this.localStorage.store('token', data.token);
        this.localStorage.store('refreshToken', data.refreshToken);
        return true
      }));
  }

  logout() {
    this.localStorage.clear('userId');
        this.localStorage.clear('username');
        this.localStorage.clear('token');
        this.localStorage.clear('refreshToken');
  }

  isLoggedIn(): Observable<boolean> {
    const token = this.getJwtToken();
    
    if (token === null) {
      return of(false);
    } else {
      return this.http.get<void>(this.baseUrl + '/auth/validate-token?token=' + token)
        .pipe(
          map(() => true),
          catchError(() => {
            const refreshToken = this.getRefreshToken();
            const username = this.getUsername();
            
            if (refreshToken === null || username === null) {
              return of(false);
            } else {
              return this.http.get<{ token: string }>(this.baseUrl + '/auth/generate-new-token?refreshToken=' + refreshToken + '&username=' + username)
                .pipe(
                  map((data) => {
                    this.localStorage.store('token', data.token);
                    return true;
                  }),
                  catchError(() => of(false))
                );
            }
          })
        );
    }
  }

  refreshToken() {
    const refreshToken = this.getRefreshToken();
    const username = this.getUsername();

    return this.http.get<{ token: string }>(this.baseUrl + '/auth/generate-new-token?refreshToken=' + refreshToken + '&username=' + username)
      .pipe(tap(response => {
        this.localStorage.store('token', response.token);
      }));
  }

  getJwtToken() {
    return this.localStorage.retrieve('token');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getUserId() {
    return this.localStorage.retrieve('userId');
  }

  getUsername() {
    return this.localStorage.retrieve('username');
  }
}
