// src/app/services/leave-type.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LeaveType {
  leaveTypeId?: number;
  name: string;
  description: string;
  annualLimit: number;
  carryForward: boolean;
  encashable: boolean;
  isActive: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class LeaveTypeService {

  private baseUrl = '/api/leave-types';

  constructor(private http: HttpClient) { }

  getAllLeaveTypes(): Observable<LeaveType[]> {
    return this.http.get<LeaveType[]>(this.baseUrl);
  }

  createLeaveType(leaveType: LeaveType): Observable<LeaveType> {
    return this.http.post<LeaveType>(this.baseUrl, leaveType);
  }

  updateLeaveType(id: number, leaveType: LeaveType): Observable<LeaveType> {
    return this.http.put<LeaveType>(`${this.baseUrl}/${id}`, leaveType);
  }

  deleteLeaveType(id: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }
}