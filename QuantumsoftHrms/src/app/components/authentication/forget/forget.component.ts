import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from "@angular/router";

@Component({
  selector: 'app-forget',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './forget.component.html',
  styleUrls: ['./forget.component.css']
})
export class ForgetComponent {

  email = '';
  message = '';
  messageColor = '';

  constructor(private router: Router) {}

  onSubmit() {
    if (!this.email) {
      this.message = 'Please enter your email address';
      this.messageColor = 'red';
      return;
    }

    const localData = localStorage.getItem('usersSignup');

    if (!localData) {
      this.message = '❌ No registered users found.';
      this.messageColor = 'red';
      return;
    }

    const users = JSON.parse(localData);

    if (!Array.isArray(users)) {
      this.message = '❌ Invalid user data.';
      this.messageColor = 'red';
      return;
    }

    const userFound = users.find(
      (u: any) => u.email?.toLowerCase() === this.email.toLowerCase()
    );

    if (userFound) {
      this.message = '✅ Password recovery link sent successfully!';
      this.messageColor = 'green';
      alert('Password recovery link sent successfully!');
      this.email = '';
    } else {
      this.message = '❌ Email not found in our system.';
      this.messageColor = 'red';
    }
  }

  goBackToLogin() {
    this.router.navigate(['/authentication']);
  }
}
