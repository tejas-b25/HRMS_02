import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ExportType, ReportService } from '../../../../../services/report.service';

@Component({
  selector: 'app-attendance-report',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './attendance-report.component.html',
  styleUrls: ['./attendance-report.component.css']
})
export class AttendanceReportComponent {

  fromDate = '';
  toDate = '';
  exportType: ExportType = 'csv';
  loading = false;
  message = '';

  constructor(
    private location: Location,
    private reportService: ReportService
  ) {}

  goBack() {
    this.location.back();
  }

  generate(form: NgForm) {
    if (!this.fromDate || !this.toDate) {
      form.control.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.message = '';

    this.reportService.exportReport(
      'Attendance',
      this.exportType,
      this.fromDate,
      this.toDate
    ).subscribe({
      next: res => { this.message = res; this.loading = false; },
      error: () => { this.message = 'Failed to generate report'; this.loading = false; }
    });
  }
}
