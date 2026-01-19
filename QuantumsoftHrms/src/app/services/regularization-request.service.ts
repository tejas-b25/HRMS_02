import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export type RegStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface RegularizationRequestPayload {
  employeeId: number;
  attendanceDate: string;      // yyyy-MM-dd
  requestedInTime: string;     // ISO datetime
  requestedOutTime: string;    // ISO datetime
  reason: string;
}

export interface RegularizationResponse {
  attendanceRegId: number;
  employeeId: number;
  employeeName: string;
  attendanceId: number;
  attendanceDate: string;
  existingInTime: string;
  existingOutTime: string;
  requestedInTime: string;
  requestedOutTime: string;
  reason: string;
  status: RegStatus;
  approverId: number | null;
  approverName: string | null;
  approverRole: string | null;
}

@Injectable({
  providedIn: 'root'
})
export class RegularizationService {

  private baseUrl = '/api/regularization';

  constructor(private http: HttpClient) {}

  create(payload: RegularizationRequestPayload)
    : Observable<RegularizationResponse> {
    return this.http.post<RegularizationResponse>(
      `${this.baseUrl}/create`,
      payload
    );
  }

  getAll(): Observable<RegularizationResponse[]> {
    return this.http.get<RegularizationResponse[]>(
      `${this.baseUrl}/all`
    );
  }

  approve(regId: number, approverId: number)
    : Observable<RegularizationResponse> {
    return this.http.put<RegularizationResponse>(
      `${this.baseUrl}/approve/${regId}/${approverId}`,
      {}
    );
  }

  reject(regId: number, approverId: number)
    : Observable<RegularizationResponse> {
    return this.http.put<RegularizationResponse>(
      `${this.baseUrl}/reject/${regId}/${approverId}`,
      {}
    );
  }
}
