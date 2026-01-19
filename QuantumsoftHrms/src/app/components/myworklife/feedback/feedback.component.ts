import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface FeedbackRequest {
  employee: string;
  message: string;
  status: 'pending' | 'completed' | 'draft' | 'given';
}

@Component({
  selector: 'app-feedback',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent {
  activeTab: string = 'Received';
  showRequestModal: boolean = false;
  showGiveModal: boolean = false;

  employeeSearch: string = '';
  message: string = '';

  feedbackRequests: FeedbackRequest[] = [];

  tabs = ['Received', 'Given', 'Pending Requests', 'Drafts'];

  get pendingRequests() {
    return this.feedbackRequests.filter(r => r.status === 'pending');
  }

  get draftRequests() {
    return this.feedbackRequests.filter(r => r.status === 'draft');
  }

  get givenFeedbacks() {
    return this.feedbackRequests.filter(r => r.status === 'given');
  }

  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  openRequestModal() {
    this.showRequestModal = true;
    this.showGiveModal = false;

  }

  openGiveModal() {
    this.showRequestModal = false;

    this.showGiveModal = true;

  }

  closeRequestModal(saveAsDraft: boolean = false) {
    if (saveAsDraft && (this.employeeSearch.trim() || this.message.trim())) {
      const draftRequest: FeedbackRequest = {
        employee: this.employeeSearch || 'Unnamed Employee',
        message: this.message || '(No message)',
        status: 'draft'
      };
      this.feedbackRequests.push(draftRequest);
      this.activeTab = 'Drafts';
    }
    this.resetForm();
    this.showRequestModal = false;
  }

  closeGiveModal(saveAsDraft: boolean = false) {
    if (saveAsDraft && (this.employeeSearch.trim() || this.message.trim())) {
      const draftFeedback: FeedbackRequest = {
        employee: this.employeeSearch || 'Unnamed Employee',
        message: this.message || '(No message)',
        status: 'draft'
      };
      this.feedbackRequests.push(draftFeedback);
      this.activeTab = 'Drafts';
    }
    this.resetForm();
    this.showGiveModal = false;
  }

  submitRequest() {
    if (this.employeeSearch.trim() && this.message.trim()) {
      const newRequest: FeedbackRequest = {
        employee: this.employeeSearch,
        message: this.message,
        status: 'pending'
      };
      this.feedbackRequests.push(newRequest);
      this.closeRequestModal();
      this.activeTab = 'Pending Requests';
    } else {
      alert('Please fill all required fields.');
    }
  }

  submitGivenFeedback() {
    if (this.employeeSearch.trim() && this.message.trim()) {
      const newFeedback: FeedbackRequest = {
        employee: this.employeeSearch,
        message: this.message,
        status: 'given'
      };
      this.feedbackRequests.push(newFeedback);
      this.closeGiveModal();
      this.activeTab = 'Given';
    } else {
      alert('Please fill all required fields.');
    }
  }

  resetForm() {
    this.employeeSearch = '';
    this.message = '';
  }
}