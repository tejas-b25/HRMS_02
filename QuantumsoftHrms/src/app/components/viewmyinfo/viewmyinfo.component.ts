import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { EditProfileComponent } from '../../viewinfoupdate/edit/edit.component';
import { UpdateProfileComponent } from '../../viewinfoupdate/update/update.component';

@Component({
  selector: 'app-viewinfo',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './viewmyinfo.component.html',
  styleUrls: ['./viewmyinfo.component.css']
})
export class ViewinfoComponent {
  constructor(private router: Router) {}

  employee = {
    id: 'qs12355',
    name: 'Priya Tavare',
    designation: 'Full Stack Developer',
    qualification: 'B.Tech CSE',
    department: 'Frontend',
    email: 'priyatavare@quantumsoftcompany.com',
    phone: '+91-9876543210',
    photoUrl: '/assets/profile.jpeg'
  };
}
