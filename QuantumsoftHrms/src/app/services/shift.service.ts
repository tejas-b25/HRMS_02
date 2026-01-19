import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ShiftService {

  private baseUrl = '/api/shifts';

  constructor(private http: HttpClient) {}

  /* ================= SHIFT MASTER ================= */

  createShift(data: any): Observable<any> {
    return this.http.post(this.baseUrl, data);
  }

  getAllShifts(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  getShiftById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  updateShift(id: number, data: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, data);
  }

  deleteShift(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }

  /* ================= SHIFT ASSIGNMENT ================= */

  assignShift(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/assign`, data);
  }

  getShiftByEmployee(employeeId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/employee/${employeeId}`);
  }
}