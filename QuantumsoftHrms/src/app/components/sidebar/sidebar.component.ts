import { HostListener } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  isCollapsed: boolean = false;
  currentSection = 'Home';
  isScrolled = false;
  // Dynamic open/close state for dropdowns
  openDropdown: { [label: string]: boolean } = {};
  constructor(private router: Router) { }
  // Sidebar menu structure - 12 sections exactly
  menu = [
    { label: 'Home', icon: 'fa-solid fa-house', route: '/app/home' },
    // { label: 'AccountInfo', icon: 'fa-solid fa-house', route: '/AccountInfo' },
    // { label: 'Engage', icon: 'fa fa-broadcast-tower', route: '/app/engage' },
    { label: 'Engage', icon: 'fa fa-broadcast-tower', route: '/app/engage' },
    { label: 'My worklife', icon: 'fa fa-th', route: '/myworklife',
      children:[
         { label: 'Feedback', route: '/app/feedback' },
        { label: 'Kudos', route: '/app/kudos' }
      ]
    }, 
    {
      label: 'To Do',
      icon: 'fa fa-clipboard-list',
      children: [
        { label: 'Tasks', route: '/app/todo/tasks' },
        { label: 'Review', route: '/app/todo/review' }
      ]
    },
    {
      label: 'Salary',
      icon:'fa-sharp fa-solid fa-hand-holding-dollar',
      children: [
        { label: 'Payslips', route: '/app/salary/payslips' },
        { label: 'YTD Reports', route: '/app/salary/Ytd-reports' },
        { label: 'IT Statement', route: '/app/salary/it-statements' },
        { label: 'IT Declaration', route: '/app/salary/it-declaration' },
        { label: 'loan-advances', route: '/app/salary/loan-advances' },
        { label: 'Reimbursement', route: '/app/salary/reimbursement' },
        { label: 'Proof Of Investment', route: '/app/salary/proof-investment' },
        { label: 'Salary Revision', route: '/app/salary/salary-revision' }
      ]
    },
    {
      label: 'Leave',
      icon: 'fa-solid fa-person-walking-arrow-right',
      children: [
        { label: 'Leave Apply', route: '/app/leave/apply' },
        { label: 'Leave Balance', route: '/app/leave/balance' },
        { label: 'Leave Calendar', route: '/app/leave/calendar' },
        { label: 'Holiday Calendar', route: '/app/holiday' }
      ]
    },                                                      
    {
      label: 'Attendance',
      icon: 'fa fa-check-square',
      children: [
        { label: 'Attendance Info', route: '/app/attendance-management/attendance-info' },
        { label: 'Regularization & Permission', route: '/app/attendance-management/regularization-permission' }
      ]
    },           
    {
      label: 'Document Center',
      icon: 'fa fa-file-alt',
      route: '/app/document-center'
    },
    {
      label: 'People',
      icon: 'fa-solid fa-users',
      route: '/app/people'
    },
    {
      label: 'Help Desk',
      icon: 'fa-solid fa-circle-question',
      route: '/app/Helpdesk1'
    },
    {
      label: 'Request Hub',
      icon: 'fa-solid fa-hubspot',
      route: '/app/Requesthub1'
    },
    {
      label: 'WorkFlow Delegates',
      icon: 'fa-solid fa-user-check',
      route: '/app/workflow-delegates'
    },   {
  label: 'Announcements',
  icon: 'fa-solid fa-bullhorn',
  route: '/app/announcements'
}
,          
        { 
  label: 'timesheet Management',
  icon: 'fa fa-inbox',
  route: '/admin/timesheet-management'
},
    
  ]; 
  ngOnInit() {
    const stored = localStorage.getItem('sidebarCollapsed');
    if (stored !== null) {
      this.isCollapsed = stored === 'true';
    }
  }
  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.isScrolled = window.scrollY > 12;
  }
  
   Onquicklinks() {
    //  alert('You  dont have any notifications!');
    this.router.navigate(['/app/AccountInfo']); 
  }

    notifications() {
       alert('You  dont have any notifications!');
          this.router.navigate(['/app/home']);
    // this.router.navigate(['/app/AccountInfo']); 

  }
  logout() {
    alert('You have been logged out!');
    this.router.navigate(['/']);
  }
  updateSection(title: string) {
    this.currentSection = title;
  }                  
  toggleCollapse() {
    this.isCollapsed = !this.isCollapsed;
    localStorage.setItem('sidebarCollapsed', this.isCollapsed.toString());
  }
  toggleDropdown(label: string) {
    const isOpen = this.openDropdown[label];
    this.closeAllDropdowns();
    this.openDropdown[label] = !isOpen;
  }
  closeAllDropdowns() {
    this.openDropdown = {};
  }


  navigateToAccountInfo() {
    this.router.navigate(['/app/AccountInfo']); 
  }

  //  quicklinks() {
  //   this.router.navigate(['/app/AccountInfo']); 
  // }


  //   notifications() {
  //   // this.router.navigate(['/app/AccountInfo']); 

  // }
}