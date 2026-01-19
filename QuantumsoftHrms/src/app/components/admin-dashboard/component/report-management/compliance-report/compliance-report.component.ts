import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ExportType, ReportService } from '../../../../../services/report.service';

@Component({
  selector: 'app-compliance-report',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './compliance-report.component.html',
  styleUrls: ['./compliance-report.component.css']
})
export class ComplianceReportComponent {

  title = 'Compliance Report';

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
      'ComplianceSummary',
      this.exportType,
      this.fromDate,
      this.toDate
    ).subscribe({
      next: res => {
        this.message = res;
        this.loading = false;
      },
      error: () => {
        this.message = 'Failed to generate compliance report';
        this.loading = false;
      }
    });
  }
}
