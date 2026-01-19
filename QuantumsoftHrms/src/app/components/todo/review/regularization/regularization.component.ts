import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-regularization',
  imports:[CommonModule,FormsModule],
  standalone: true,
  templateUrl: './regularization.component.html',
  styleUrls: ['./regularization.component.css']
})
export class RegularizationComponent {
activeTab: any;
setTab(arg0: string) {
throw new Error('Method not implemented.');
}
  showForm = false;
  selectedTab: string = 'active';
  searchText = '';
  // employees = ['Nimish Yelne', 'Priya Sharma', 'Rahul Patil', 'Sneha Joshi'];
  filteredEmployees: string[] = [];

  requests: any[] = [];
  newRequest = { date: '', employee: '', status: 'Pending' };

  toggleForm() {
    this.showForm = !this.showForm;
  }

  filterEmployees() {
    // const text = this.searchText.toLowerCase();
    // this.filteredEmployees = this.employees.filter(emp =>
    //   emp.toLowerCase().includes(text)
    // );
  }

  selectEmployee(emp: string) {
    this.newRequest.employee = emp;
    this.searchText = emp;
    this.filteredEmployees = [];
  }

  addRequest() {
    if (this.newRequest.date && this.newRequest.employee) {
      this.requests.push({ ...this.newRequest });
      this.newRequest = { date: '', employee: '', status: 'Pending' };
      this.showForm = false;
      this.searchText = '';
    }
  }

  approveRequest(index: number) {
    this.requests[index].status = 'Approved';
  }

  rejectRequest(index: number) {
    this.requests[index].status = 'Rejected';
  }

  deleteRequest(index: number) {
    this.requests.splice(index, 1);
  }
}