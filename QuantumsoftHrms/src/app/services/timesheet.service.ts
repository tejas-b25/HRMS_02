import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TimesheetService {

  private apiUrl = '/api/timesheets';

  constructor(private http: HttpClient) {}

  submitTimesheet(payload: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/submit`, payload);
  }

  getTimesheetsByEmployee(employeeId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/employee/${employeeId}`);
  }
}
