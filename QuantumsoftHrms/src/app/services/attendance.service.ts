




 
// // src/app/services/attendance.service.ts
// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
 
// /* --------------------------------------------------
//    Interfaces used for various attendance features
// --------------------------------------------------- */
 
// export interface PermissionDetail {
//   type: string;
//   applied: string;
//   approved: string;
//   considered?: string;
//   fromDate: string;
//   toDate: string;
//   appliedBy: string;
//   appliedOn: string;
//   reason: string;
// }
 
// export interface AttendanceEntry {
//   date: string;
//   status: string;
//   firstIn?: string;
//   lastOut?: string;
//   lateIn?: string;
//   earlyOut?: string;
//   breakHrs?: string;
//   totalWork?: string;
//   actualWork?: string;
//   remarks?: string;
//   permissions?: PermissionDetail[];
// }
 
// export interface ClockInPayload {
//   employeeId: string;
//   clockInTime: string;
//   geoLocation: string;
//   deviceType: string;
//   attendanceDate: string;
//   status: string;
// }
 
// export interface ClockOutPayload {
//   employeeId: number;
//   clockOutTime: string;
//   geoLocation: string;
//   deviceType: string;
// }
 
// /** 🔥 Backend API model for summary */
// export interface AttendanceSummary {
//   attendanceId?: number;
//   attendanceDate: string;
//   status: string;
//   clockInTime?: string;
//   clockOutTime?: string;
//   geoLocation?: string;
//   deviceType?: string;
//   shiftId?: number;
// }
 
// @Injectable({
//   providedIn: 'root'
// })
// export class AttendanceService {
 
//   /* Backend Base URL */
//   private readonly baseUrl = '/api/attendance';
 
//   /* LocalStorage legacy support */
//   private localKey = 'attendanceData';
//   private entries: AttendanceEntry[] = [];
 
//   constructor(private http: HttpClient) {
//     this.loadLocal();
//   }
 
//   /* ---------------------------------------
//        LOCAL STORAGE - OLD UI Support
//   ----------------------------------------*/
//   private loadLocal() {
//     const raw = localStorage.getItem(this.localKey);
//     if (raw) {
//       try { this.entries = JSON.parse(raw); }
//       catch { this.entries = []; }
//     }
//   }
 
//   private saveLocal() {
//     localStorage.setItem(this.localKey, JSON.stringify(this.entries));
//   }
 
//   /* Legacy APIs used by attendanceinfo.component */
//   getAll(): AttendanceEntry[] {
//     return [...this.entries];
//   }
 
//   getByDate(date: string): AttendanceEntry | undefined {
//     return this.entries.find(e => e.date === date);
//   }
 
//   getAvgWorkHours(): string {
//     const valid = this.entries.filter(e => e.totalWork);
//     if (!valid.length) return '00:00';
 
//     const total = valid.reduce((sum, e) => {
//       const [h, m] = e.totalWork!.split(':').map(Number);
//       return sum + h * 60 + m;
//     }, 0);
 
//     const avg = Math.round(total / valid.length);
//     return this.formatMinutes(avg);
//   }
 
//   getAvgActualWork(): string {
//     const valid = this.entries.filter(e => e.actualWork);
//     if (!valid.length) return '00:00';
 
//     const total = valid.reduce((sum, e) => {
//       const [h, m] = e.actualWork!.split(':').map(Number);
//       return sum + h * 60 + m;
//     }, 0);
 
//     const avg = Math.round(total / valid.length);
//     return this.formatMinutes(avg);
//   }
 
//   private formatMinutes(mins: number): string {
//     const hh = Math.floor(mins / 60);
//     const mm = mins % 60;
//     return `${hh.toString().padStart(2, '0')}:${mm.toString().padStart(2, '0')}`;
//   }
 
//   getPenaltyDays(): number {
//     return this.entries.filter(e => e.status === 'A').length;
//   }
 
//   upsertLocal(entry: AttendanceEntry): void {
//     const idx = this.entries.findIndex(e => e.date === entry.date);
//     if (idx >= 0) this.entries[idx] = entry;
//     else this.entries.push(entry);
//     this.saveLocal();
//   }
 
//   deleteLocal(date: string): void {
//     this.entries = this.entries.filter(e => e.date !== date);
//     this.saveLocal();
//   }
 
//   /* ---------------------------------------
//        BACKEND CLOCK-IN / CLOCK-OUT APIs
//   ----------------------------------------*/
//   clockIn(body: any): Observable<any> {
//     return this.http.post<any>(`${this.baseUrl}/clock-in`, body);
//   }
 
//   clockOut(): Observable<any> {
//     return this.http.post<any>(`${this.baseUrl}/clock-out`, {});
//   }
 
 
//   /* ---------------------------------------
//        🔥 Attendance Summary API (NEW)
//   ----------------------------------------*/
//   getAttendanceSummary(
//     employeeId: number | string,
//     startDate: string,
//     endDate: string
//   ): Observable<AttendanceSummary[]> {
//     return this.http.get<AttendanceSummary[]>(
//       `${this.baseUrl}/${employeeId}/summary`,
//       { params: { startDate, endDate } }
//     );
//   }
// }
 
 




// src/app/services/attendance.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/* --------------------------------------------------
   Interfaces used for various attendance features
--------------------------------------------------- */

export interface PermissionDetail {
  type: string;
  applied: string;
  approved: string;
  considered?: string;
  fromDate: string;
  toDate: string;
  appliedBy: string;
  appliedOn: string;
  reason: string;
}

export interface AttendanceEntry {
  date: string;
  status: string;
  firstIn?: string;
  lastOut?: string;
  lateIn?: string;
  earlyOut?: string;
  breakHrs?: string;
  totalWork?: string;
  actualWork?: string;
  remarks?: string;
  permissions?: PermissionDetail[];
}

export interface ClockInPayload {
  employeeId: string;
  clockInTime: string;
  geoLocation: string;
  deviceType: string;
  attendanceDate: string;
  status: string;
}

export interface ClockOutPayload {
  employeeId: number;
  clockOutTime: string;
  geoLocation: string;
  deviceType: string;
}

/** 🔥 Backend API model for summary */
export interface AttendanceSummary {
  attendanceId?: number;
  attendanceDate: string;
  status: string;
  clockInTime?: string;
  clockOutTime?: string;
  geoLocation?: string;
  deviceType?: string;
  shiftId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {

  /* Backend Base URL */
  private readonly baseUrl = '/api/attendance';

  /* LocalStorage legacy support */
  private localKey = 'attendanceData';
  private entries: AttendanceEntry[] = [];

  constructor(private http: HttpClient) {
    this.loadLocal();
  }

  /* ---------------------------------------
       LOCAL STORAGE - OLD UI Support
  ----------------------------------------*/
  private loadLocal() {
    const raw = localStorage.getItem(this.localKey);
    if (raw) {
      try { this.entries = JSON.parse(raw); }
      catch { this.entries = []; }
    }
  }

  private saveLocal() {
    localStorage.setItem(this.localKey, JSON.stringify(this.entries));
  }

  /* Legacy APIs used by attendanceinfo.component */
  getAll(): AttendanceEntry[] {
    return [...this.entries];
  }

  getByDate(date: string): AttendanceEntry | undefined {
    return this.entries.find(e => e.date === date);
  }

  getAvgWorkHours(): string {
    const valid = this.entries.filter(e => e.totalWork);
    if (!valid.length) return '00:00';

    const total = valid.reduce((sum, e) => {
      const [h, m] = e.totalWork!.split(':').map(Number);
      return sum + h * 60 + m;
    }, 0);

    const avg = Math.round(total / valid.length);
    return this.formatMinutes(avg);
  }

  getAvgActualWork(): string {
    const valid = this.entries.filter(e => e.actualWork);
    if (!valid.length) return '00:00';

    const total = valid.reduce((sum, e) => {
      const [h, m] = e.actualWork!.split(':').map(Number);
      return sum + h * 60 + m;
    }, 0);

    const avg = Math.round(total / valid.length);
    return this.formatMinutes(avg);
  }

  private formatMinutes(mins: number): string {
    const hh = Math.floor(mins / 60);
    const mm = mins % 60;
    return `${hh.toString().padStart(2, '0')}:${mm.toString().padStart(2, '0')}`;
  }

  getPenaltyDays(): number {
    return this.entries.filter(e => e.status === 'A').length;
  }

  upsertLocal(entry: AttendanceEntry): void {
    const idx = this.entries.findIndex(e => e.date === entry.date);
    if (idx >= 0) this.entries[idx] = entry;
    else this.entries.push(entry);
    this.saveLocal();
  }

  deleteLocal(date: string): void {
    this.entries = this.entries.filter(e => e.date !== date);
    this.saveLocal();
  }

  /* ---------------------------------------
       BACKEND CLOCK-IN / CLOCK-OUT APIs
  ----------------------------------------*/
  clockIn(body: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/clock-in`, body);
  }

  clockOut(): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/clock-out`, {});
  }


  /* ---------------------------------------
       🔥 Attendance Summary API (NEW)
  ----------------------------------------*/
  getAttendanceSummary(
    employeeId: number | string,
    startDate: string,
    endDate: string
  ): Observable<AttendanceSummary[]> {
    return this.http.get<AttendanceSummary[]>(
      `${this.baseUrl}/${employeeId}/summary`,
      { params: { startDate, endDate } }
    );
  }
}