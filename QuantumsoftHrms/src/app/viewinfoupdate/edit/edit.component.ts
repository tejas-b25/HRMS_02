import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditProfileComponent {
  constructor(private router: Router) {}

  employee = {
    name: 'Priya Tavare',
    designation: 'Full Stack Developer',
    email: 'priyatavare@quantumsoftcompany.com',
    phone: '+91-9876543210'
  };

  onSubmit() {
   
    alert('Profile updated (Edit)!');
    this.router.navigate(['/viewinfo']); 
  }
}
