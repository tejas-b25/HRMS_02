import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface HelpdeskItem {
  category: string;
  subject: string;
  description: string;
  priority: string;
  status: 'active' | 'closed';
}

@Component({
  selector: 'app-helpdesk',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './helpdesk.component.html',
  styleUrls: ['./helpdesk.component.css']
})
export class HelpdeskComponent1 implements OnInit {
  activeTab: 'active' | 'closed' = 'active';
  showForm = false;

  helpdeskItems: HelpdeskItem[] = [];

  newRequest: HelpdeskItem = {
    category: '',
    subject: '',
    description: '',
    priority: '',
    status: 'active'
  };

  ngOnInit() {
    // Load data from local storage when page loads
    const storedData = localStorage.getItem('helpdeskItems');
    if (storedData) {
      this.helpdeskItems = JSON.parse(storedData);
    }
  }

  switchTab(tab: 'active' | 'closed') {
    this.activeTab = tab;
    this.showForm = false;
  }

  openForm() {
    this.showForm = true;
  }

  cancelForm() {
    this.showForm = false;
    this.newRequest = { category: '', subject: '', description: '', priority: '', status: 'active' };
  }

  submitRequest() {
    if (!this.newRequest.category || !this.newRequest.subject || !this.newRequest.description || !this.newRequest.priority) {
      alert('Please fill all fields');
      return;
    }

    this.helpdeskItems.push({ ...this.newRequest });
    this.saveToLocalStorage();

    this.newRequest = { category: '', subject: '', description: '', priority: '', status: 'active' };
    this.showForm = false;
  }

  closeRequest(index: number) {
    const activeItems = this.activeItems;
    const itemToClose = activeItems[index];
    itemToClose.status = 'closed';

    // Update main list with changed item
    const mainIndex = this.helpdeskItems.findIndex(i => i.subject === itemToClose.subject && i.description === itemToClose.description);
    if (mainIndex !== -1) {
      this.helpdeskItems[mainIndex].status = 'closed';
    }

    this.saveToLocalStorage();
  }

  get activeItems() {
    return this.helpdeskItems.filter(i => i.status === 'active');
  }

  get closedItems() {
    return this.helpdeskItems.filter(i => i.status === 'closed');
  }

  // ✅ Save data to local storage
  saveToLocalStorage() {
    localStorage.setItem('helpdeskItems', JSON.stringify(this.helpdeskItems));
  }
}