import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PayrollProcessRequest {
  employeeId: number;
  month: string;          // e.g. "NOV-2027"
  structureId: number;
  bankReferenceNo: string;
}

@Injectable({
  providedIn: 'root'
})
export class PayrollProcessService {

  private readonly baseUrl = '/api/payroll/process';

  constructor(private http: HttpClient) {}

  processPayroll(payload: PayrollProcessRequest): Observable<any> {
    return this.http.post<any>(this.baseUrl, payload);
  }
}
