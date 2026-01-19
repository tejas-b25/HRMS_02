import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export type RegStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface RegularizationCreatePayload {
  employeeId: number;
  attendanceDate: string;
  requestedInTime: string;
  requestedOutTime: string;
  reason: string;
}

export interface RegularizationResponse {
  attendanceRegId: number;
  employeeId: number;
  employeeName: string;
  attendanceDate: string;
  requestedInTime: string;
  requestedOutTime: string;
  reason: string;
  status: RegStatus;
}

@Injectable({ providedIn: 'root' })
export class EmployeeRegularizationService {

  private baseUrl = '/api/regularization';

  constructor(private http: HttpClient) {}

  // EMPLOYEE → CREATE REQUEST
  create(payload: RegularizationCreatePayload): Observable<RegularizationResponse> {
    return this.http.post<RegularizationResponse>(
      `${this.baseUrl}/create`,
      payload
    );
  }

  // EMPLOYEE → VIEW OWN REQUESTS
  getByEmployee(employeeId: number): Observable<RegularizationResponse[]> {
    return this.http.get<RegularizationResponse[]>(
      `${this.baseUrl}/employee/${employeeId}`
    );
  }

  // ADMIN → VIEW ALL
  getAll(): Observable<RegularizationResponse[]> {
    return this.http.get<RegularizationResponse[]>(`${this.baseUrl}/all`);
  }

  // ADMIN → APPROVE
  approve(id: number, approverId: number) {
    return this.http.put(
      `${this.baseUrl}/approve/${id}/${approverId}`,
      {}
    );
  }

  // ADMIN → REJECT
  reject(id: number, approverId: number) {
    return this.http.put(
      `${this.baseUrl}/reject/${id}/${approverId}`,
      {}
    );
  }
}
