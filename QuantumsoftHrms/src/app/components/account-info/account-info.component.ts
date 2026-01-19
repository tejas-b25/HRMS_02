import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

interface AccountInfo {
  // Basic Information
  id: string;
  name: string;
  officialBirthday: string;
  department: string;
  location: string;
  designation: string;

  // Profile Information
  nickname: string;
  wishMeOn: string;
  timezone: string;
  biography: string;
  socialMedia: string;
  
  // Security
  lastLogin: string;
  loginHistory: any[];
}

@Component({
  selector: 'app-account-info',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './account-info.component.html',
  styleUrls: ['./account-info.component.css']
})
export class AccountInfoComponent {
  accountInfo: AccountInfo = {
    id: '050022458',
    name: 'Priyanka Tavare',
    officialBirthday: '13 Mar',
    department: 'IT',
    location: 'Solapur, Maharashtra',
    designation: 'Full Stack Developer',
    nickname: 'priya',
    wishMeOn: '31 Mar',
    timezone: 'IST (UTC+5:30)',
    biography: 'Passionate full stack developer with expertise in modern web technologies.',
    socialMedia: 'LinkedIn: priyanka-tavare',
    lastLogin: '2024-01-15 09:30:45',
    loginHistory: [
      { date: '2024-01-15 09:30:45', device: 'Chrome Windows', location: 'Solapur' },
      { date: '2024-01-14 14:20:30', device: 'Firefox Mac', location: 'Solapur' },
      { date: '2024-01-13 08:15:20', device: 'Mobile Android', location: 'Pune' }
    ] 
  };
  // Track editable fields
  editableFields = {
    nickname: false,
    wishMeOn: false,
    timezone: false,
    biography: false,
    socialMedia: false
  };
  // Show/hide sections
  showLoginHistory = false;
  showChangePassword = false;
// toggleLoginHistory=false;

  // Temporary storage for edited values
  editedAccountInfo: AccountInfo = { ...this.accountInfo };

  // Toggle edit mode for a field
  toggleEdit(field: keyof typeof this.editableFields) {
    this.editableFields[field] = !this.editableFields[field];
    
    if (this.editableFields[field]) {
      this.editedAccountInfo = { ...this.accountInfo };
    }
  }
  // Save a specific field
  saveField(field: keyof typeof this.editableFields) {
    this.accountInfo[field] = this.editedAccountInfo[field];
    this.editableFields[field] = false;
    
    // Save to localStorage
    this.saveToStorage();
    
    this.showNotification(`${field.charAt(0).toUpperCase() + field.slice(1)} updated successfully!`);
  }

  // Cancel editing
  cancelEdit(field: keyof typeof this.editableFields) {  
    this.editableFields[field] = false;
    this.editedAccountInfo = { ...this.accountInfo };
  }

  // Toggle login history view
  toggleLoginHistory() {
    this.showLoginHistory = !this.showLoginHistory;
    this.showChangePassword = false;
  }
  // Toggle change password view
  toggleChangePassword() {
    this.showChangePassword = !this.showChangePassword;
    this.showLoginHistory = false;
  }
  // Save to localStorage
  private saveToStorage() {
    localStorage.setItem('accountInfo', JSON.stringify(this.accountInfo));
  }
  // Load from localStorage
  ngOnInit() {
    const savedData = localStorage.getItem('accountInfo');
    if (savedData) {
      this.accountInfo = { ...this.accountInfo, ...JSON.parse(savedData) };
      this.editedAccountInfo = { ...this.accountInfo };
    }
  }
  // Show notification
  private showNotification(message: string) {
    // You can implement a proper notification service here
    alert(message);
  }
  // Check if any field is being edited
  isAnyFieldEditing(): boolean {
    return Object.values(this.editableFields).some(value => value);
  }
// Toggle login history view
// toggleLoginHistory() {
//   this.showLoginHistory = !this.showLoginHistory;
//   this.showChangePassword = false; // Close change password if open
// }

// // Toggle change password view
// toggleChangePassword() {
//   this.showChangePassword = !this.showChangePassword;
//   this.showLoginHistory = false; // Close login history if open
// }

}