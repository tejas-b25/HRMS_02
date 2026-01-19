import { CommonModule } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { AttendanceService } from '../../../../services/attendance.service';

@Component({
  selector: 'app-admin-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit, OnDestroy {

  currentDate!: string;
  currentTime!: string;
  signedIn = false;
  showSwipeModal = false;

  swipeRecords: { time: string; type: string; address: string }[] = [];

  private clockTimer!: any;
  private autoSignOutTimer!: any;

  // 🔒 SHIFT END TIME (18:30)
  private readonly SHIFT_END_HOUR = 18;
  private readonly SHIFT_END_MINUTE = 30;

  constructor(private attendanceService: AttendanceService) {}

  /* ------------------------------------
        INIT & DESTROY
  -------------------------------------*/
  ngOnInit() {
    this.updateDateTime();
    this.clockTimer = setInterval(() => this.updateDateTime(), 1000);

    this.loadTodaySwipeData();

    // 🔥 Auto sign-out checker (runs every 1 min)
    this.autoSignOutTimer = setInterval(
      () => this.checkAutoSignOut(),
      60000
    );
  }

  ngOnDestroy(): void {
    clearInterval(this.clockTimer);
    clearInterval(this.autoSignOutTimer);
  }

  /* ------------------------------------
        DATE & TIME
  -------------------------------------*/
  updateDateTime() {
    const now = new Date();

    this.currentDate = now.toLocaleDateString('en-GB', {
      day: '2-digit',
      month: 'short',
      year: 'numeric',
    });

    this.currentTime = now.toLocaleTimeString('en-GB');
  }

  /* ------------------------------------
        SIGN IN / SIGN OUT
  -------------------------------------*/
  toggleSign() {
    const storedDate = localStorage.getItem('attendanceDate');
    const today = this.getTodayKey();

    // ❌ Prevent clock-out for previous day
    if (this.signedIn && storedDate !== today) {
      alert('Previous day attendance auto-closed. Please sign in again.');
      this.resetAttendance();
      return;
    }

    if (!this.signedIn) {
      this.clockIn();
    } else {
      this.clockOut();
    }
  }
  resetAttendance() {
    localStorage.removeItem('todayAttendance');
    localStorage.removeItem('attendanceDate');
    this.signedIn = false;
    this.swipeRecords = [];
  }


  clockIn() {
    const payload = {
      geoLocation: '19.07,72.87',
      deviceType: 'WEB'
    };

    this.attendanceService.clockIn(payload).subscribe({
      next: (res) => {
        localStorage.setItem('todayAttendance', JSON.stringify(res));
        localStorage.setItem('attendanceDate', this.getTodayKey());

        this.signedIn = true;
        this.loadTodaySwipeData();
      },
      error: (err) => {
        if (err.error?.message?.includes('Already clocked in')) {
          this.signedIn = true;
          this.loadTodaySwipeData();
        } else {
          alert(err.error?.message || 'Clock-In failed!');
        }
      }
    });
  }

  clockOut() {
    this.attendanceService.clockOut().subscribe({
      next: (res) => {
        localStorage.setItem('todayAttendance', JSON.stringify(res));
        this.signedIn = false;
        this.loadTodaySwipeData();
      },
      error: (err) => {
        if (err.error?.message?.includes('Already clocked out')) {
          this.signedIn = false;
          this.loadTodaySwipeData();
        } else {
          alert(err.error?.message || 'Clock-Out failed!');
        }
      }
    });
  }

  /* ------------------------------------
        AUTO SIGN-OUT LOGIC 🔥
  -------------------------------------*/
  checkAutoSignOut() {
    if (!this.signedIn) return;

    const now = new Date();

    // ⏰ Shift end time
    const shiftEnd = new Date();
    shiftEnd.setHours(
      this.SHIFT_END_HOUR,
      this.SHIFT_END_MINUTE,
      0,
      0
    );

    // 🔴 Auto sign-out after shift
    if (now >= shiftEnd) {
      console.log('⏰ Shift ended. Auto sign-out.');
      this.clockOut();
      return;
    }

    // 🌙 Midnight safety sign-out
    const storedDate = localStorage.getItem('attendanceDate');
    if (storedDate && storedDate !== this.getTodayKey()) {
      console.log('🌙 New day detected. Auto sign-out.');
      this.clockOut();
    }
  }

  /* ------------------------------------
        SWIPE DATA
  -------------------------------------*/
loadTodaySwipeData() {
  this.swipeRecords = [];

  const stored = localStorage.getItem('todayAttendance');
  const storedDate = localStorage.getItem('attendanceDate');

  const today = this.getTodayKey();

  // ❌ Yesterday record → invalidate
  if (!stored || storedDate !== today) {
    console.warn('Old attendance detected. Resetting state.');

    localStorage.removeItem('todayAttendance');
    localStorage.removeItem('attendanceDate');

    this.signedIn = false;
    return;
  }

  const data = JSON.parse(stored);

  if (data.clockInTime) {
    this.swipeRecords.push({
      time: this.formatTime(data.clockInTime),
      type: 'IN',
      address: '-'
    });
    this.signedIn = true;
  }

  if (data.clockOutTime) {
    this.swipeRecords.push({
      time: this.formatTime(data.clockOutTime),
      type: 'OUT',
      address: '-'
    });
    this.signedIn = false;
  }
}


  formatTime(dateTime: string): string {
    return new Date(dateTime).toLocaleTimeString('en-GB');
  }

  /* ------------------------------------
        MODAL
  -------------------------------------*/
  openSwipeModal(event: Event) {
    event.preventDefault();
    this.showSwipeModal = true;
  }

  closeSwipeModal() {
    this.showSwipeModal = false;
  }

  /* ------------------------------------
        UTIL
  -------------------------------------*/
  private getTodayKey(): string {
    return new Date().toISOString().split('T')[0];
  }
}