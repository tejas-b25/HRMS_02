import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  EmployeeRegularizationService,
  RegularizationCreatePayload,
  RegularizationResponse
} from '../../../services/employee-regularization.service';

@Component({
  selector: 'app-permission',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './regularization-permission.component.html',
  styleUrls: ['./regularization-permission.component.css']
})
export class RegularizationPermissionComponent implements OnInit {

  activeTab = 'apply';
  showQuickAddForm = false;
  selectedDay: number | null = null;
  currentRemarks = '';

  currentMonth = new Date().getMonth();
  currentYear = new Date().getFullYear();

  months = ['January','February','March','April','May','June',
            'July','August','September','October','November','December'];
  dayNames = ['S','M','T','W','T','F','S'];

  // 🔴 REQUIRED BY YOUR BACKEND
  employeeId = 57;

  pendingPermissions: RegularizationResponse[] = [];
  permissionHistory: RegularizationResponse[] = [];

  currentEntry = {
    permissionType: 'Select',
    fromTime: this.getCurrentTime(),
    toTime: this.addMinutes(this.getCurrentTime(), 30),
    reason: ''
  };

  constructor(private regService: EmployeeRegularizationService) {}

  ngOnInit(): void {
    this.loadMyPermissions();
  }

  applyQuickAdd() {
    if (!this.selectedDay || !this.currentEntry.reason) {
      alert('Select date and enter reason');
      return;
    }

    const date = this.apiDate();

    const payload: RegularizationCreatePayload = {
      employeeId: this.employeeId,
      attendanceDate: date,
      requestedInTime: `${date}T${this.currentEntry.fromTime}:00`,
      requestedOutTime: `${date}T${this.currentEntry.toTime}:00`,
      reason: this.currentEntry.reason
    };

    this.regService.create(payload).subscribe(() => {
      this.loadMyPermissions();
      this.resetQuickAdd();
    });
  }

  isToday(day: number): boolean {
  const today = new Date();
  return (
    day === today.getDate() &&
    this.currentMonth === today.getMonth() &&
    this.currentYear === today.getFullYear()
  );
}


  loadMyPermissions() {
    this.regService.getByEmployee(this.employeeId).subscribe(res => {
      this.pendingPermissions = res.filter(r => r.status === 'PENDING');
      this.permissionHistory = res.filter(r => r.status !== 'PENDING');
    });
  }

  /* ===== Calendar Helpers ===== */
  get daysInMonth() {
    return Array.from(
      { length: new Date(this.currentYear, this.currentMonth + 1, 0).getDate() },
      (_, i) => i + 1
    );
  }

  get blanks() {
    return Array(new Date(this.currentYear, this.currentMonth, 1).getDay()).fill(0);
  }

  selectDate(day: number) {
    this.selectedDay = day;
    this.showQuickAddForm = true;
  }

  getFormattedDate() {
    if (!this.selectedDay) return '';
    return new Date(this.currentYear, this.currentMonth, this.selectedDay)
      .toLocaleDateString('en-GB', { day:'2-digit', month:'short', year:'numeric' });
  }

  apiDate() {
    return new Date(this.currentYear, this.currentMonth, this.selectedDay!)
      .toISOString().split('T')[0];
  }

  getCurrentTime() {
    const d = new Date();
    return `${d.getHours().toString().padStart(2,'0')}:${d.getMinutes().toString().padStart(2,'0')}`;
  }

  addMinutes(time: string, mins: number) {
    const [h,m] = time.split(':').map(Number);
    const d = new Date();
    d.setHours(h, m + mins);
    return `${d.getHours().toString().padStart(2,'0')}:${d.getMinutes().toString().padStart(2,'0')}`;
  }

  resetQuickAdd() {
    this.showQuickAddForm = false;
    this.currentEntry.reason = '';
  }
cancelQuickAdd() {
  this.resetQuickAdd();
}

  setTab(tab: string) { this.activeTab = tab; }
  toggleQuickAdd() { this.showQuickAddForm = !this.showQuickAddForm; }
  prevMonth() { this.currentMonth === 0 ? (this.currentMonth = 11, this.currentYear--) : this.currentMonth--; }
  nextMonth() { this.currentMonth === 11 ? (this.currentMonth = 0, this.currentYear++) : this.currentMonth++; }
}
