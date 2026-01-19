import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LeavePayload {
  employeeId: number;
  leaveTypeId: number;
  startDate: string; // yyyy-MM-dd
  endDate: string;   // yyyy-MM-dd
  reason: string;
}

@Injectable({
  providedIn: 'root'
})
export class LeaveService {

  private apiUrl = '/api/leaves';

  constructor(private http: HttpClient) {}

  applyLeave(payload: LeavePayload, file?: File): Observable<any> {
    const formData = new FormData();

    // IMPORTANT: backend expects JSON STRING
    formData.append('leave', JSON.stringify(payload));

    if (file) {
      formData.append('file', file);
    }

    return this.http.post(`${this.apiUrl}/apply`, formData);
  }
}
