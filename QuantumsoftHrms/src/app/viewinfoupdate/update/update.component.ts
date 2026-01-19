import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateProfileComponent {
  constructor(private router: Router) {}

  employee = {
    qualification: 'B.Tech CSE',
    department: 'Frontend',
    photoUrl: '/assets/profile.jpeg'
  };

  onSubmit() {
    alert('Profile updated (Update)!');
    this.router.navigate(['/viewinfo']); // Back to profile view
  }
}
