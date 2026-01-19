import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

type LeaveRecord = {
  id: number;
  type: string;
  fromDate: string;
  toDate: string;
  days: number;
  status: 'PENDING' | 'APPROVED' | 'REJECTED' | 'CANCELED';
  appliedOn: string;
  applyingTo: string[];
  ccTo: string[];
  contact: string;
  reason: string;
  fileName?: string;
};
@Component({
  selector: 'app-leave-calendar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './leave-calender.component.html',
  styleUrls: ['./leave-calender.component.css']
})
export class LeaveCalenderComponent implements OnInit {
  filterType: 'All' | 'Me' | 'My Team' = 'All';
  allLeaves: LeaveRecord[] = [];
  filteredLeaves: LeaveRecord[] = [];
  days: any[] = [];
  selectedDate: string | null = null;
  selectedLeaves: LeaveRecord[] = [];
  currentMonth = new Date().getMonth();
  currentYear = new Date().getFullYear();
  availableYears: number[] = [];
  months = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
  ];
  holidaysByMonth: { [key: string]: { date: string; name: string }[] } = {
    JAN: [{ date: '01 Wed', name: 'New Year' }, { date: '26 Sun', name: 'Republic Day' }],
    FEB: [{ date: '26 Wed', name: 'Mahashivratri' }],
    MAR: [{ date: '29 Sat', name: 'Good Friday' }, { date: '30 Sun', name: 'Eid' }],
    APR: [{ date: '18 Fri', name: 'Good Friday' }],
    MAY: [{ date: '01 Thu', name: 'Maharashtra Day' }],
    JUN: [],
    JUL: [{ date: '06 Sun', name: 'Muharram' }],
    AUG: [
      { date: '10 Sun', name: 'Raksha Bandhan' },
      { date: '15 Fri', name: 'Independence Day' },
      { date: '16 Sat', name: 'Janmashtami' }
    ],
    SEP: [{ date: '05 Fri', name: 'Milad-un-Nabi (Eid-e-Milad)' }],
    OCT: [
      { date: '02 Thu', name: 'Gandhi Jayanti / Dussehra' },
      { date: '21 Tue', name: 'Diwali' }
    ],
    NOV: [],
    DEC: [{ date: '25 Thu', name: 'Christmas' }]
  };
  currentUser = localStorage.getItem('currentUser') || 'Me';
  currentTeam = localStorage.getItem('currentUserTeam') || 'Team A';
  ngOnInit() {
    this.generateAvailableYears();
    this.loadLeaves();
    this.updateCalendar();
  }
  generateAvailableYears() {
    const current = new Date().getFullYear();
    this.availableYears = [current - 2, current - 1, current, current + 1, current + 2];
  }
  onMonthOrYearChange() {
    this.updateCalendar();
  }
  loadLeaves() {
    const data = localStorage.getItem('leaveHistory');
    this.allLeaves = data ? JSON.parse(data).filter((l: LeaveRecord) => l.status === 'APPROVED') : [];
  }
  applyFilter() {
    if (this.filterType === 'All') {
      this.filteredLeaves = this.allLeaves.filter(
        l => l.applyingTo.includes(this.currentUser) || l.ccTo.includes(this.currentUser) || l.applyingTo.length === 0
      );
    } else if (this.filterType === 'Me') {
      this.filteredLeaves = this.allLeaves.filter(
        l => !l.applyingTo.includes(this.currentUser) && !l.ccTo.includes(this.currentUser)
      );
    } else if (this.filterType === 'My Team') {
      this.filteredLeaves = this.allLeaves.filter(
        l => !l.applyingTo.includes(this.currentUser) && !l.ccTo.includes(this.currentUser)
      );
    }
    this.markLeaveDates();
  }
  updateCalendar() {
    this.generateCalendar();
    this.applyFilter();
  }
  markLeaveDates() {
    this.days.forEach(day => {
      if (!day) return;
      const dayDate = new Date(this.currentYear, this.currentMonth, day.date);
      dayDate.setHours(0, 0, 0, 0);

      const leave = this.filteredLeaves.find(l => {
        const from = new Date(l.fromDate); from.setHours(0, 0, 0, 0);
        const to = new Date(l.toDate); to.setHours(0, 0, 0, 0);
        return dayDate >= from && dayDate <= to;
      });
      if (leave) {
        day.hasLeave = true;
        day.leaveType = leave.type.toLowerCase().includes('sick') ? 'sick' :
                        leave.type.toLowerCase().includes('casual') ? 'casual' : 'paid';
      } else {
        day.hasLeave = false;
        day.leaveType = null;
      }
    });
  }
  generateCalendar() {
    const totalDays = new Date(this.currentYear, this.currentMonth + 1, 0).getDate();
    const firstDay = new Date(this.currentYear, this.currentMonth, 1).getDay();
    this.days = [];
    for (let i = 0; i < firstDay; i++) this.days.push(null);

    for (let d = 1; d <= totalDays; d++) {
      const date = new Date(this.currentYear, this.currentMonth, d);
      this.days.push({
        date: d,
        hasLeave: false,
        leaveType: null,
        isWeekend: date.getDay() === 0 || date.getDay() === 6,
        isHoliday: this.isHoliday(date)
      });
    }
  }
  selectDate(day: any) {
    if (!day || day.isWeekend) return;

    const selectedDay = new Date(this.currentYear, this.currentMonth, day.date);
    selectedDay.setHours(0, 0, 0, 0);
    this.selectedDate =  `${this.currentYear}-${this.months[this.currentMonth]}-${day.date}`;

    this.selectedLeaves = this.filteredLeaves.filter(l => {
      const from = new Date(l.fromDate); from.setHours(0, 0, 0, 0);
      const to = new Date(l.toDate); to.setHours(0, 0, 0, 0);
      return selectedDay >= from && selectedDay <= to;
    });
  }
  isHoliday(date: Date): boolean {
    const monthKey = this.months[date.getMonth()].substring(0, 3).toUpperCase();
    const dayStr = date.getDate().toString().padStart(2, '0');
    const monthHolidays = this.holidaysByMonth[monthKey] || [];
    return monthHolidays.some(h => h.date.startsWith(dayStr));
  }
  getHolidayName(day: any): string | null {
    if (!day?.isHoliday) return null;
    const monthKey = this.months[this.currentMonth].substring(0, 3).toUpperCase();
    const dayStr = day.date.toString().padStart(2, '0');
    const monthHolidays = this.holidaysByMonth[monthKey] || [];
    const match = monthHolidays.find(h => h.date.startsWith(dayStr));
    return match ? match.name : null;
  }
}