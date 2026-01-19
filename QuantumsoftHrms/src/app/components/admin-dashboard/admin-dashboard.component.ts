import { Component, HostListener, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  isCollapsed = false;
  isScrolled = false;
  currentSection = 'Home';

  openDropdown: { [key: string]: boolean } = {};

  constructor(private router: Router) {}

 menu = [
  { label: 'Home', icon: 'fa-solid fa-gauge-high', route: '/admin/home' },
  { label: 'Register', icon: 'fa-solid fa-user-check', route: '/admin/register' },

  {
    label: 'Employee',
    icon: 'fa-solid fa-people-group',
    children: [
      { label: 'Add employee', icon: 'fa-solid fa-user-plus', route: '/admin/employee/add-employee' },
      { label: 'Employee list', icon: 'fa-solid fa-id-card', route: '/admin/employee/employee-list' },
      { label: 'Banking', icon: 'fa-solid fa-building-columns', route: '/admin/banking' }
    ]
  },

  { label: 'Department', icon: 'fa-solid fa-sitemap', route: '/admin/department' },

  {
    label: 'Payroll',
    icon: 'fa-solid fa-wallet',
    children: [
      { label: 'Salary structure', icon: 'fa-solid fa-layer-group', route: '/admin/payroll/salary-structure' },
      { label: 'Payroll processing', icon: 'fa-solid fa-calculator', route: '/admin/payroll/payroll-processing' }
    ]
  },
{
  label: 'Holiday Calendar',
  icon: 'fa-solid fa-calendar-days',
  route: '/admin/holidays-calendar'
},
{
    label: 'Documents',                     
    icon: 'fa-solid fa-file-shield',
    route: '/admin/documents'
  },
  {
    label: 'Attendance',
    icon: 'fa-solid fa-calendar-days',
    children: [
      { label: 'Regularization request', icon: 'fa-solid fa-clipboard-check', route: '/admin/attendance/regularization-request' },
      { label: 'Attendance report', icon: 'fa-solid fa-chart-line', route: '/admin/attendance/attendance-report' }
    ]
  },

  {
    label: 'Leave',
    icon: 'fa-solid fa-umbrella-beach',
    children: [
      { label: 'Leave type', icon: 'fa-solid fa-tags', route: '/admin/leave/leave-type' },
      { label: 'Leave management', icon: 'fa-solid fa-calendar-minus', route: '/admin/leave/leave-management' }
    ]
  },

  {
    label: 'Performance Management',
    icon: 'fa-solid fa-chart-pie',
    children: [
      { label: 'Feedback', icon: 'fa-solid fa-comments', route: '/admin/feedback' },
      { label: 'KPI Master', icon: 'fa-solid fa-bullseye', route: '/admin/kpi-master' },
      { label: 'Performance Dashboard', icon: 'fa-solid fa-chart-area', route: '/admin/performance-dashboard' },
      { label: 'Performance Review', icon: 'fa-solid fa-star-half-stroke', route: '/admin/performance-review' }
    ]
  },

  {
    label: 'Shift Management',
    icon: 'fa-solid fa-clock-rotate-left',
    children: [
      { label: 'Shift Assignment', icon: 'fa-solid fa-calendar-plus', route: '/admin/shift/shift-assignment' },
      { label: 'Shift Master', icon: 'fa-solid fa-clock', route: '/admin/shift/shift-master' }
    ]
  },

  {
    label: 'Benefits',
    icon: 'fa-solid fa-gift',
    children: [
      { label: 'Benefit Master', icon: 'fa-solid fa-box-open', route: '/admin/benefits/benefit-master' },
      { label: 'Benefit Provider', icon: 'fa-solid fa-hand-holding-heart', route: '/admin/benefits/benefit-provider' },
      { label: 'Benefit Mapping', icon: 'fa-solid fa-link', route: '/admin/benefits/benefit-mapping' }
    ]
  },

  {
    label: 'Compliances',
    icon: 'fa-solid fa-shield-halved',
    children: [
      { label: 'Add Compliance', icon: 'fa-solid fa-file-circle-plus', route: '/admin/compliances/add-compliance' },
      { label: 'Compliance Mapping', icon: 'fa-solid fa-diagram-project', route: '/admin/compliances/compliance-mapping' },
      { label: 'Compliance Documents', icon: 'fa-solid fa-folder-open', route: '/admin/compliances/compliance-documents' },
      { label: 'Compliance Alerts', icon: 'fa-solid fa-bell', route: '/admin/compliances/compliance-alerts' }
    ]
  },

  {
    label: 'Report Management',
    icon: 'fa-solid fa-file-lines',
    children: [
      { label: 'Leave Report', icon: 'fa-solid fa-plane-circle-check', route: '/admin/leave-report' },
      { label: 'Compliance Report', icon: 'fa-solid fa-shield-check', route: '/admin/compliance-report' },
      { label: 'Attendance Report', icon: 'fa-solid fa-user-clock', route: '/admin/attendance-report' },
      { label: 'Employee Report', icon: 'fa-solid fa-users-viewfinder', route: '/admin/employeeMaster-report' }
    ]
  },
  {
    label: 'Announcement',
    icon: 'fa-solid fa-gift',
    route: '/admin/announcement'
  },
  // {
  //   label: 'AnnouncementreadStatus',
  //   icon: 'fa-solid fa-gift',
  //   route: '/admin/announcementreadstatus'
  // },

  // { label: 'Announcement', icon: 'fa-solid fa-bullhorn', route: '/admin/admin-announcement-management' },  ///
  { label: 'Tax Management', icon: 'fa-solid fa-file-invoice-dollar', route: '/admin/tax-management' },
  { label: 'Employee Tax Details', icon: 'fa-solid fa-receipt', route: '/admin/employee-tax-details' },
  { label: 'Timesheet Management', icon: 'fa-solid fa-stopwatch', route: '/admin/timesheet-management' }
];

  ngOnInit() {
    const stored = localStorage.getItem('sidebarCollapsed');
    this.isCollapsed = stored === 'true';
  }

  @HostListener('window:scroll')
  onScroll() {
    this.isScrolled = window.scrollY > 10;
  }

  toggleDropdown(label: string) {
    this.openDropdown = { [label]: !this.openDropdown[label] };
  }

  updateSection(label: string) {
    this.currentSection = label;
    this.openDropdown = {};
  }

  goBack() {
    this.router.navigate(['/']);
  }

  logout() {
    this.router.navigate(['/']);
  }
}
