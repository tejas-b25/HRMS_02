import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { AdminHolidaysService, Holiday } from '../../../../services/admin-holidays.service';

@Component({
  selector: 'app-admin-holidays-calendar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-holidays-calendar.component.html',
  styleUrls: ['./admin-holidays-calendar.component.css']
})
export class AdminHolidaysCalendarComponent implements OnInit {

  holidays: Holiday[] = [];

  selectedYear = new Date().getFullYear();
  selectedMonth = new Date().getMonth(); // 0 = Jan
  selectedRegion = 'India';

  months = [
    'January','February','March','April',
    'May','June','July','August',
    'September','October','November','December'
  ];

  weekDays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  calendarDays: (number | null)[] = [];

  isLoading = false;

  // Modal
  showModal = false;
  isEditMode = false;
  editId: number | null = null;

  holidayForm: any = {
    holidayName: '',
    holidayDate: '',
    region: '',
    optional: false,
    holidayType: 'NATIONAL',
    description: ''
  };

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


  openCreateModal() {
    this.isEditMode = false;
    this.editId = null;
    this.holidayForm = {
      holidayName: '',
      holidayDate: '',
      region: this.selectedRegion,
      optional: false,
      holidayType: 'NATIONAL',
      description: ''
    };
    this.showModal = true;
  }

  openEditModal(h: Holiday) {
    this.isEditMode = true;
    this.editId = h.holidayId;
    this.holidayForm = { ...h };
    this.showModal = true;
  }

  saveHoliday(form: NgForm) {
    if (form.invalid) return;

    const api$ = this.isEditMode
      ? this.holidayService.updateHoliday(this.editId!, this.holidayForm)
      : this.holidayService.createHoliday(this.holidayForm);

    api$.subscribe(() => {
      this.showModal = false;
      this.loadCalendar();
    });
  }

  deleteHoliday(id: number) {
    if (!confirm('Delete this holiday?')) return;
    this.holidayService.deleteHoliday(id).subscribe(() => this.loadCalendar());
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