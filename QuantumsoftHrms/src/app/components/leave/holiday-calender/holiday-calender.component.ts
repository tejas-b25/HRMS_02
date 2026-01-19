import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminHolidaysService, Holiday } from '../../../services/admin-holidays.service';


@Component({
  selector: 'app-holiday-calendar',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './holiday-calender.component.html',
  styleUrls: ['./holiday-calender.component.css']
})
export class HolidayCalenderComponent implements OnInit {

  holidays: Holiday[] = [];

  selectedYear = new Date().getFullYear();
  selectedMonth = new Date().getMonth();
  selectedRegion = 'India';

  months = [
    'January','February','March','April',
    'May','June','July','August',
    'September','October','November','December'
  ];

  weekDays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  calendarDays: (number | null)[] = [];

  isLoading = false;

  constructor(private holidayService: AdminHolidaysService) {}

  ngOnInit(): void {
    this.generateCalendar();
    this.loadCalendar();
  }

  loadCalendar() {
    this.isLoading = true;
    this.holidayService
      .getCalendar(this.selectedYear, this.selectedRegion)
      .subscribe(res => {
        this.holidays = res;
        this.generateCalendar();
        this.isLoading = false;
      });
  }

  generateCalendar() {
    const firstDay = new Date(this.selectedYear, this.selectedMonth, 1).getDay();
    const totalDays = new Date(this.selectedYear, this.selectedMonth + 1, 0).getDate();

    const days: (number | null)[] = [];
    for (let i = 0; i < firstDay; i++) days.push(null);
    for (let d = 1; d <= totalDays; d++) days.push(d);

    this.calendarDays = days;
  }

  prevMonth() {
    if (this.selectedMonth === 0) {
      this.selectedMonth = 11;
      this.selectedYear--;
    } else {
      this.selectedMonth--;
    }
    this.generateCalendar();
  }

  nextMonth() {
    if (this.selectedMonth === 11) {
      this.selectedMonth = 0;
      this.selectedYear++;
    } else {
      this.selectedMonth++;
    }
    this.generateCalendar();
  }

  getHolidayByDay(day: number) {
    return this.holidays.filter(h => {
      const d = new Date(h.holidayDate);
      return (
        d.getFullYear() === this.selectedYear &&
        d.getMonth() === this.selectedMonth &&
        d.getDate() === day
      );
    });
  }

  /** 🔹 Next upcoming holiday (ONLY ONE) */
  get upcomingHolidays(): Holiday[] {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    return this.holidays
      .filter(h => {
        const d = new Date(h.holidayDate);
        d.setHours(0, 0, 0, 0);
        return d >= today;
      })
      .sort((a, b) =>
        new Date(a.holidayDate).getTime() - new Date(b.holidayDate).getTime()
      )
      .slice(0, 1); // ✅ ONLY ONE HOLIDAY
  }


  /** 🔹 Visible holidays for selected month */
  get visibleHolidays(): Holiday[] {
    return this.holidays.filter(h => {
      const d = new Date(h.holidayDate);
      return (
        d.getFullYear() === this.selectedYear &&
        d.getMonth() === this.selectedMonth
      );
    });
  }
}