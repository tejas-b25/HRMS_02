import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Compliance {
  complianceId?: number;
  complianceName: string;
  complianceType: string;
  description: string;
  isActive: boolean;
  createdBy: string;
  updatedBy: string;
}

@Injectable({
  providedIn: 'root'
})
export class ComplianceService {

  private readonly baseUrl = '/api/compliance';

  constructor(private http: HttpClient) {}

  createCompliance(data: Compliance): Observable<Compliance> {
    return this.http.post<Compliance>(this.baseUrl, data);
  }

  getAllCompliances(): Observable<Compliance[]> {
    return this.http.get<Compliance[]>(this.baseUrl);
  }

  updateCompliance(id: number, data: Compliance): Observable<Compliance> {
    return this.http.put<Compliance>(`${this.baseUrl}/${id}`, data);
  }

  deleteCompliance(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }

    // Assign compliance to employee
  assignCompliance(payload: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/employee`, payload);
  }

  // Get employee compliances
  getEmployeeCompliances(employeeId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
  }
}