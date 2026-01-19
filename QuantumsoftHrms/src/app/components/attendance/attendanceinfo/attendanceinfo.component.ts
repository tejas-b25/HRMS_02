import { Component, OnInit } from '@angular/core';
import { AttendanceService, AttendanceEntry } from '../../../services/attendance.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
// import { Router } from '@angular/router';
@Component({
  selector: 'app-attendanceinfo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './attendanceinfo.component.html',
  styleUrls: ['./attendanceinfo.component.css']
})
export class AttendanceinfoComponent implements OnInit {
  currentYear = 0;
  currentMonth = 0;
  months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
  dayNames = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];
  blanks: number[] = [];
  daysInMonth: number[] = [];

  attendanceEntries: AttendanceEntry[] = [];
// goToInsights?="";
  selectedDate?: string;
  selectedEntry?: AttendanceEntry;
  
  processedOn = '';

  constructor(private svc: AttendanceService) {}

  ngOnInit(): void {
    const now = new Date();
    this.currentYear = now.getFullYear();
    this.currentMonth = now.getMonth();
    this.processedOn = new Date().toLocaleString();
    this.generateCalendar();
    this.attendanceEntries = this.svc.getAll();
  }

  generateCalendar(): void {
    const first = new Date(this.currentYear, this.currentMonth, 1);
    this.blanks = Array(first.getDay()).fill(0);
    const cnt = new Date(this.currentYear, this.currentMonth + 1, 0).getDate();
    this.daysInMonth = Array.from({ length: cnt }, (_, i) => i + 1);
  }

  prevMonth(): void {
    if (this.currentMonth === 0) {
      this.currentYear--;
      this.currentMonth = 11;
    } else {
      this.currentMonth--;
    }
    this.generateCalendar();
    this.clearSelection();
  }

  nextMonth(): void {
    if (this.currentMonth === 11) {
      this.currentYear++;
      this.currentMonth = 0;
    } else {
      this.currentMonth++;
    }
    this.generateCalendar();
    this.clearSelection();
  }

  clearSelection(): void {
    this.selectedDate = undefined;
    this.selectedEntry = undefined;
  }

  dayDate(day: number): string {
    const mm = (this.currentMonth + 1).toString().padStart(2, '0');
    const dd = day.toString().padStart(2, '0');
    return `${this.currentYear}-${mm}-${dd}`;
  }

  getStatusClass(dateStr: string): string {
    const e = this.attendanceEntries.find(x => x.date === dateStr);
    if (e) {
      switch (e.status) {
        case 'P': return 'status-present';
        case 'A': return 'status-absent';
        case 'O': return 'status-off';
        case 'H': return 'status-holiday';
        case 'L': return 'status-leave';
        case 'OD': return 'status-on-duty';
      }
    }
    const d = new Date(dateStr).getDay();
    if (d === 0 || d === 6) return 'status-off';
    return '';
  }

  getLabel(dateStr: string): string {
    const e = this.attendanceEntries.find(x => x.date === dateStr);
    if (e && e.status) {
      return e.status;
    }
    const d = new Date(dateStr).getDay();
    if (d === 0 || d === 6) return 'O';
    return '';
  }

  selectDate(day: number): void {
    const ds = this.dayDate(day);
    this.selectedDate = ds;
    const e = this.svc.getByDate(ds);
    if (e) {
      this.selectedEntry = { ...e };
    } else {
      this.selectedEntry = {
        date: ds,
        status: '',
        firstIn: '',
        lastOut: '',
        lateIn: '',
        earlyOut: '',
        breakHrs: '',
        totalWork: '',
        actualWork: '',
        remarks: '',
        permissions: []
      };
    }
  }

  getStatusLabel(status?: string): string {
    switch (status) {
      case 'P': return 'Present';
      case 'A': return 'Absent';
      case 'O': return 'Off Day';
      case 'H': return 'Holiday';
      case 'L': return 'Leave';
      case 'OD': return 'On Duty';
      default: return '-';
    }
  }
get permissions() {
  return this.selectedEntry?.permissions ?? [];
}
  get avgWork(): string {
    return this.svc.getAvgWorkHours();
  }
  get avgActualWork(): string {
    return this.svc.getAvgActualWork();
  }
  get penaltyDays(): number {
    return this.svc.getPenaltyDays();
  }

  goToInsights(): void {
//  this.router.navigate(['/home']); 
console.log("this is a insights page");

  }

//   get permissions(): PermissionDetail[] {
//   return this.selectedEntry?.permissions ?? [];
// }
}
