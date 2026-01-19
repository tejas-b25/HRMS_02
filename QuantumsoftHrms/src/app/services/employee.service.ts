// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { environment } from '../../environments/environment';
// import { Observable, throwError } from 'rxjs';
// import { catchError } from 'rxjs/operators';

// export interface EmployeePayload {
//   id?: number;           // database id
//   employeeId?: number;   // business id (optional, if your entity has it)

//   firstName: string;
//   lastName: string;
//   username: string;
//   password: string;
//   dateOfBirth: string;      // yyyy-MM-dd
//   gender: string;
//   contactNumber: string;
//   email: string;
//   address: string;
//   bloodGroup?: string;
//   dateOfJoining: string;    // yyyy-MM-dd
//   designation: string;
//   department: string;
//   employmentType: string;
//   role: string;
//   status: string;

//   //employee id 

//   reportingManager: { id: number };
//   hrbp: { id: number };
// }

// @Injectable({ providedIn: 'root' })
// export class EmployeeService {

//   // environment.apiBaseUrl = "/api"
//   private baseUrl = `${environment.apiBaseUrl}/employees`;

//   constructor(private http: HttpClient) {}

//   // CREATE  -> POST /api/employees
//   createEmployee(payload: EmployeePayload): Observable<EmployeePayload> {
//     return this.http.post<EmployeePayload>(this.baseUrl, payload).pipe(
//       catchError(this.handleError)
//     );
//   }

//   // GET ALL -> GET /api/employees/all
//   getEmployees(): Observable<EmployeePayload[]> {
//     return this.http.get<EmployeePayload[]>(`${this.baseUrl}/all`).pipe(
//       catchError(this.handleError)
//     );
//   }

//   // GET BY ID -> GET /api/employees/{id}
//   getEmployeeById(id: number): Observable<EmployeePayload> {
//     return this.http.get<EmployeePayload>(`${this.baseUrl}/${id}`).pipe(
//       catchError(this.handleError)
//     );
//   }

//   // UPDATE -> PUT /api/employees/{id}
//   updateEmployee(id: number, payload: EmployeePayload): Observable<EmployeePayload> {
//     return this.http.put<EmployeePayload>(`${this.baseUrl}/${id}`, payload).pipe(
//       catchError(this.handleError)
//     );
//   }

//   // DELETE -> DELETE /api/employees/{id}
//   deleteEmployee(id: number): Observable<string> {
//   return this.http
//     .delete(`${this.baseUrl}/${id}`, { responseType: 'text' })
//     .pipe(
//       catchError(this.handleError)
//     );
// }

//   private handleError(err: HttpErrorResponse) {
//     let message = 'An error occurred while calling employees API';

//     if (err.error) {
//       if (typeof err.error === 'string') message = err.error;
//       else if ((err.error as any).message) message = (err.error as any).message;
//       else message = JSON.stringify(err.error);
//     } else if (err.message) {
//       message = err.message;
//     }

//     return throwError(() => ({ status: err.status, message }));
//   }
// }
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface EmployeePayload {
  id?: number;                // ✅ DB PRIMARY KEY
  employeeId?: number;        // optional business id (ignored)

  firstName: string;
  lastName: string;
  username: string;
  password: string;
  dateOfBirth: string;
  gender: string;
  contactNumber: string;
  email: string;
  address: string;
  bloodGroup?: string;
  dateOfJoining: string;
  designation: string;
  department: string;
  employmentType: string;
  role: string;
  status: string;

  reportingManager: { id: number };
  hrbp: { id: number };
}

@Injectable({ providedIn: 'root' })
export class EmployeeService {

  private baseUrl = `${environment.apiBaseUrl}/employees`;

  constructor(private http: HttpClient) {}

  getEmployees(): Observable<EmployeePayload[]> {
    return this.http.get<EmployeePayload[]>(`${this.baseUrl}/all`)
      .pipe(catchError(this.handleError));
  }

  getEmployeeById(id: number): Observable<EmployeePayload> {
    return this.http.get<EmployeePayload>(`${this.baseUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  createEmployee(payload: EmployeePayload): Observable<EmployeePayload> {
    return this.http.post<EmployeePayload>(this.baseUrl, payload)
      .pipe(catchError(this.handleError));
  }

  updateEmployee(id: number, payload: EmployeePayload): Observable<EmployeePayload> {
    return this.http.put<EmployeePayload>(`${this.baseUrl}/${id}`, payload)
      .pipe(catchError(this.handleError));
  }

  deleteEmployee(id: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' })
      .pipe(catchError(this.handleError));
  }

  private handleError(err: HttpErrorResponse) {
    let message = 'Employee API error';

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
