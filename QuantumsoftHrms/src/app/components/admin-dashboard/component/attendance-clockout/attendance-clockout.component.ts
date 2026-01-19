// src/app/components/attendance/attendance-clockout/attendance-clockout.component.ts
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface ClockOutRequest {
  employeeId: number;      // number this time, as in your JSON
  clockOutTime: string;    // "2025-10-10T18:30:00"
  geoLocation: string;     // "19.7582,75.3203"
  deviceType: string;      // "MOBILE"
}

@Component({
  selector: 'app-attendance-clockout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './attendance-clockout.component.html',
  styleUrls: ['./attendance-clockout.component.css']
})
export class AttendanceClockoutComponent {

  apiUrl = '/api/attendance/clock-out';

  clockOutData: ClockOutRequest = {
    employeeId: 0,
    clockOutTime: '',
    geoLocation: '',
    deviceType: 'MOBILE'
  };

  loading = false;
  successMessage = '';
  errorMessage = '';
  lastResponse: any;

  constructor(private http: HttpClient) {
    this.setDefaultTime();
  }

  // Set default clock-out time = now
  setDefaultTime() {
    const now = new Date();
    const iso = now.toISOString();       // e.g. 2025-10-10T18:30:45.123Z
    this.clockOutData.clockOutTime = iso.slice(0, 19); // 2025-10-10T18:30:45
  }

  useCurrentTime() {
    this.setDefaultTime();
  }

  onSubmit(form: NgForm) {
    this.successMessage = '';
    this.errorMessage = '';
    this.lastResponse = null;

    if (form.invalid) {
      this.errorMessage = 'Please fill all required fields.';
      return;
    }

    if (!this.clockOutData.employeeId || this.clockOutData.employeeId <= 0) {
      this.errorMessage = 'Employee ID must be a positive number.';
      return;
    }

    this.loading = true;

    this.http.post(this.apiUrl, this.clockOutData).subscribe({
      next: (res) => {
        this.loading = false;
        this.lastResponse = res;
        this.successMessage = 'Clock-out successful!';
        console.log('Clock-out response:', res);
      },
      error: (err) => {
        this.loading = false;
        console.error('Clock-out error:', err);
        this.errorMessage = err?.error?.message || 'Clock-out failed.';
      }
    });
  }
}
