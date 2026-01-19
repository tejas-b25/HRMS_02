// src/app/components/attendance/attendance-clockin/attendance-clockin.component.ts
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface ClockInRequest {
  employeeId: string;      // as in your JSON
  clockInTime: string;     // "2025-10-10T10:30:00"
  geoLocation: string;     // "19.7582,75.3203"
  deviceType: string;      // "MOBILE"
  attendanceDate: string;  // "2025-10-10"
  status: string;          // "PRESENT"
}

@Component({
  selector: 'app-attendance-clockin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './attendance-clockin.component.html',
  styleUrls: ['./attendance-clockin.component.css']
})
export class AttendanceClockinComponent {

  apiUrl = '/api/attendance/clock-in';

  clockInData: ClockInRequest = {
    employeeId: '',
    clockInTime: '',
    geoLocation: '',
    deviceType: 'MOBILE',
    attendanceDate: '',
    status: 'PRESENT'
  };

  loading = false;
  successMessage = '';
  errorMessage = '';
  lastResponse: any;

  constructor(private http: HttpClient) {
    this.setDefaults();
  }

  // Set default date & time
  setDefaults() {
    const now = new Date();

    // "YYYY-MM-DD"
    this.clockInData.attendanceDate = now.toISOString().slice(0, 10);

    // "YYYY-MM-DDTHH:mm:00"
    const iso = now.toISOString();        // e.g. 2025-10-10T10:30:45.123Z
    this.clockInData.clockInTime = iso.slice(0, 19); // 2025-10-10T10:30:45
  }

  useCurrentTime() {
    const now = new Date();
    const iso = now.toISOString();
    this.clockInData.clockInTime = iso.slice(0, 19);
  }

  onSubmit(form: NgForm) {
    this.successMessage = '';
    this.errorMessage = '';
    this.lastResponse = null;

    if (form.invalid) {
      this.errorMessage = 'Please fill all required fields.';
      return;
    }

    this.loading = true;

    this.http.post(this.apiUrl, this.clockInData).subscribe({
      next: (res) => {
        this.loading = false;
        this.lastResponse = res;
        this.successMessage = 'Clock-in successful!';
        console.log('Clock-in response:', res);
      },
      error: (err) => {
        this.loading = false;
        console.error('Clock-in error:', err);
        this.errorMessage = err?.error?.message || 'Clock-in failed.';
      }
    });
  }
}
