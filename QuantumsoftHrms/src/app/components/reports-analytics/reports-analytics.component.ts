import { Component } from '@angular/core';

@Component({
  selector: 'app-reports-analytics',
  standalone: true,
  template: `<h2>Reports & Analytics</h2><p>View reports and analytics here.</p>`
})
export class ReportsAnalyticsComponent {
  reports = ['Attendance Report', 'Payroll Report'];
}
