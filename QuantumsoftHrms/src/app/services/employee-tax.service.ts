import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EmployeeTaxService {

  private apiUrl = '/api/tax';

  constructor(private http: HttpClient) {}

  saveTax(payload: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/employee`, payload);
  }

  getTaxByEmployee(employeeId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/employee/${employeeId}`);
  }
}
