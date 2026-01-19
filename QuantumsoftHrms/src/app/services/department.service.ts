import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

export interface Department {
  departmentId: number;
  name: string;
  code: string;
  description?: string;
  status: string;       // e.g. 'ACTIVE' or 'INACTIVE'
  createdAt?: string;
  updatedAt?: string;
}

interface DepartmentListResponse {
  content: Department[];
  // other pagination/meta fields if your backend returns them
}

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  private baseUrl = `/api/departments`;

  constructor(private http: HttpClient) {}

  getDepartments(): Observable<Department[]> {
    return this.http.get<DepartmentListResponse>(this.baseUrl)
      .pipe(
        map(resp => resp.content || []),
        catchError(this.handleError)
      );
  }

  createDepartment(payload: Partial<Department>): Observable<Department> {
    return this.http.post<Department>(this.baseUrl, payload)
      .pipe(catchError(this.handleError));
  }

  updateDepartment(dept: Department): Observable<Department> {
    return this.http.put<Department>(`${this.baseUrl}/${dept.departmentId}`, dept)
      .pipe(catchError(this.handleError));
  }

  deleteDepartment(id: number): Observable<string> {
  return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' })
    .pipe(catchError(this.handleError));
}


  private handleError(err: HttpErrorResponse) {
    let msg = 'An error occurred calling the Departments API';
    if (err.error) {
      if (typeof err.error === 'string') msg = err.error;
      else if ((err.error as any).message) msg = (err.error as any).message;
      else msg = JSON.stringify(err.error);
    } else if (err.message) {
      msg = err.message;
    }
    return throwError(() => ({ status: err.status, message: msg }));
  }
}
//2