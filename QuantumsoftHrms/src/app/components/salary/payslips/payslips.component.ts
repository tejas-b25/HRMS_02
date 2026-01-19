import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payslips',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './payslips.component.html',
  styleUrls: ['./payslips.component.css']
})
export class PayslipsComponent {
  selectedTab: string = 'payslip'; // default selected tab

  selectTab(tab: string) {
    this.selectedTab = tab;
  }
}