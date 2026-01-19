



import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface RegisterPayload {
  username: string;
  password: string;
  email: string;
  firstName?: string;
  lastName?: string;
  // add other fields you need to send (role, status, age...) if backend expects them
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  // environment.apiBaseUrl should be like "/api"
  private baseUrl = `${environment.apiBaseUrl}`;

  constructor(private http: HttpClient) {}

  // ---------- LOGIN methods (unchanged) ----------
  userLogin(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, credentials).pipe(
      catchError(this.handleError)
    );
  }

  adminLogin(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, credentials).pipe(
      catchError(this.handleError)
    );
  }

  // ---------- REGISTER (new) ----------
  /**
   * registerUser / register - POST /api/auth/user
   * Payload shape should match your backend requirement (example in your question).
   */
  registerUser(payload: RegisterPayload): Observable<any> {
    const url = `${this.baseUrl}/auth/user`;
    return this.http.post(url, payload).pipe(
      catchError(this.handleError)
    );
  }

getAllUsers() {
  return this.http.get<any[]>('/api/auth/users');
}

deleteUser(id: number) {
  return this.http.delete(`/api/auth/user/${id}`);
}


  // alias for convenience (some components call register)
  register(payload: RegisterPayload): Observable<any> {
    return this.registerUser(payload);
  }

  signup(data: any): Observable<any> {
    // if your backend doesn't support /auth/register, this will likely 404.
    // Keep for backward-compat; prefer registerUser().
    return this.http.post(`${this.baseUrl}/auth/register`, data).pipe(
      catchError(this.handleError)
    );
  }

forgotPassword(payload: { email: string }): Observable<any> {
  return this.http
    .post(`${this.baseUrl}/auth/resetPassword`, payload)
    .pipe(catchError(this.handleError));
}

// /api/auth/resetPassword       
  private handleError(err: HttpErrorResponse) {
    let message = 'An error occurred';
    if (err.error) {
      if (typeof err.error === 'string') message = err.error;
      else if (err.error.message) message = err.error.message;
      else message = JSON.stringify(err.error);
    } else if (err.message) {
      message = err.message;
    }

    return throwError(() => ({ status: err.status, message }));
  }
}