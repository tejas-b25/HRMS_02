import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-confirmation',
  imports:[CommonModule,FormsModule],
  standalone: true,
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent {
  showForm = false;
  selectedTab: string = 'active';
  searchText = '';

  filteredEmployees: string[] = [];

  requests: any[] = [];
  newRequest = { date: '', employee: '', status: 'Pending' };

  toggleForm() {
    this.showForm = !this.showForm;
  }

  filterEmployees() {
    
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