import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ytd-reports',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ytd-reports.component.html',
  styleUrls: ['./ytd-reports.component.css']
})
export class YtdReportsComponent {
  selectedTab: string = 'ytd'; // default selected
  selectedYear: string = 'Apr 2025 - Mar 2026';
  years: string[] = [
    'Apr 2023 - Mar 2024',
    'Apr 2024 - Mar 2025',
    'Apr 2025 - Mar 2026'
  ];
  selectTab(tab: string) {
    this.selectedTab = tab;
  }
}