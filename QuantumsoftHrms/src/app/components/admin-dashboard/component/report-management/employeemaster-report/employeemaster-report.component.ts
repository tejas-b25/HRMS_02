import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ExportType, ReportService } from '../../../../../services/report.service';

@Component({
  selector: 'app-employeemaster-report',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employeemaster-report.component.html',
  styleUrls: ['./employeemaster-report.component.css']
})
export class EmployeemasterReportComponent {

  exportType: ExportType = 'csv';
  loading = false;
  message = '';

  constructor(
    private location: Location,
    private reportService: ReportService
  ) {}

  goBack() { this.location.back(); }

  generate() {
    this.loading = true;
    this.message = '';

    this.reportService.exportReport(
      'EmployeeMaster',
      this.exportType
    ).subscribe({
      next: res => { this.message = res; this.loading = false; },
      error: () => { this.message = 'Failed to generate report'; this.loading = false; }
    });
  }
}
