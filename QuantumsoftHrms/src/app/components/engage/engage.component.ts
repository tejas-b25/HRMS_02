import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface Activity {
  id: number;
  group: string;
  message: string;
  user: string;
  timeAgo: string;
}

@Component({
  selector: 'app-engage',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './engage.component.html',
  styleUrl: './engage.component.css'
})
export class EngageComponent {
  selectedFilter = 'All Activities';
  searchText = '';
  sortOrder: 'Newest' | 'Oldest' = 'Newest';
  activeForm: 'kudos' | 'poll' | 'post' | null = null;

  activities: Activity[] = [];
  filteredActivities: Activity[] = [];

  // Form inputs
  kudosUser = '';
  kudosMessage = '';
  pollQuestion = '';
  pollOption1 = '';
  pollOption2 = '';
  postMessage = '';

  ngOnInit() {
    this.activities = [
      { id: 1, group: 'Events', message: 'Happy Birthday Suvarna Thorat, have a great year ahead!', user: 'Suvarna Thorat', timeAgo: '4 hours ago' },
      { id: 2, group: 'Events', message: 'Happy Birthday Shubham Dhoke, have a great year ahead!', user: 'Shubham Dhoke', timeAgo: '2 days ago' },
      { id: 3, group: 'Kudos', message: 'Kudos to Anjali for great teamwork on HRMS Module!', user: 'Anjali', timeAgo: '3 days ago' },
      { id: 4, group: 'Polls', message: 'Vote for upcoming team outing location!', user: 'Quantumsoft HR', timeAgo: '5 days ago' },
      { id: 5, group: 'Posts', message: 'Welcome new joinees to Quantumsoft Family!', user: 'HR Team', timeAgo: '1 week ago' },
    ];
    this.applyFilter();
  }

  showForm(formType: 'kudos' | 'poll' | 'post') {
    this.activeForm = formType;
  }

  // 🟢 Submit functions
  submitKudos() {
    if (!this.kudosUser || !this.kudosMessage) return alert('Please fill all fields');
    const newActivity: Activity = {
      id: Date.now(),
      group: 'Kudos',
      message: `Kudos to ${this.kudosUser}: ${this.kudosMessage}`,
      user: 'You',
      timeAgo: 'Just now'
    };
    this.activities.unshift(newActivity);
    this.resetForms();
    this.applyFilter();
  }

  submitPoll() {
    if (!this.pollQuestion || !this.pollOption1 || !this.pollOption2) return alert('Please fill all fields');
    const newActivity: Activity = {
      id: Date.now(),
      group: 'Polls',
      message: `${this.pollQuestion} (Options: ${this.pollOption1}, ${this.pollOption2})`,
      user: 'You',
      timeAgo: 'Just now'
    };
    this.activities.unshift(newActivity);
    this.resetForms();
    this.applyFilter();
  }

  submitPost() {
    if (!this.postMessage) return alert('Please write something');
    const newActivity: Activity = {
      id: Date.now(),
      group: 'Posts',
      message: this.postMessage,
      user: 'You',
      timeAgo: 'Just now'
    };
    this.activities.unshift(newActivity);
    this.resetForms();
    this.applyFilter();
  }

  resetForms() {
    this.kudosUser = '';
    this.kudosMessage = '';
    this.pollQuestion = '';
    this.pollOption1 = '';
    this.pollOption2 = '';
    this.postMessage = '';
    this.activeForm = null;
  }

  // 🔍 Filter and Sort
  applyFilter() {
    let list = [...this.activities];

    if (this.selectedFilter !== 'All Activities') {
      list = list.filter(a => a.group === this.selectedFilter);
    }

    if (this.searchText.trim()) {
      list = list.filter(a =>
        a.message.toLowerCase().includes(this.searchText.toLowerCase()) ||
        a.user.toLowerCase().includes(this.searchText.toLowerCase())
      );
    }

    if (this.sortOrder === 'Oldest') list = list.reverse();

    this.filteredActivities = list;
  }
}