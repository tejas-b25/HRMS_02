import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ExportType, ReportService } from '../../../../../services/report.service';
@Component({
  selector: 'app-leave-report',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './leave-report.component.html',
  styleUrls: ['./leave-report.component.css']
})
export class LeaveReportComponent {

  fromDate = '';
  toDate = '';
  exportType: ExportType = 'csv';

  loading = false;
  message = '';

  constructor(
    private location: Location,
    private reportService: ReportService
  ) {}

  goBack() { this.location.back(); }

  generate(form: NgForm) {
    if (!this.fromDate || !this.toDate) {
      form.control.markAllAsTouched();
      return;
    }

    this.loading = true;

    this.reportService.exportReport(
      'Leave',
      this.exportType,
      this.fromDate,
      this.toDate
    ).subscribe({
      next: res => { this.message = res; this.loading = false; },
      error: () => { this.message = 'Failed to generate leave report'; this.loading = false; }
    });
  }
}
