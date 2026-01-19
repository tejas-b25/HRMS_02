import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-reimbursement',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './reimbursement.component.html',
  styleUrl: './reimbursement.component.css'
})
export class ReimbursementComponent {
 selectedYear: string = 'Apr 2025 - Mar 2026';

  years: string[] = [
    'Apr 2023 - Mar 2024',
    'Apr 2024 - Mar 2025',
    'Apr 2025 - Mar 2026'
  ];           
}