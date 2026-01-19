import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnouncementService } from '../../services/announcement.service';

@Component({
  selector: 'app-employee-announcement',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-announcement.component.html',
  styleUrls: ['./employee-announcement.component.css']
})
export class EmployeeAnnouncementComponent implements OnInit {

  announcements: any[] = [];
  loading = true;

  constructor(private announcementService: AnnouncementService) {}

  ngOnInit(): void {
    this.loadAnnouncements();
  }

  loadAnnouncements() {
    this.announcementService.getEmployeeAnnouncements().subscribe({
      next: res => {
        this.announcements = res;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  markRead(a: any) {
    this.announcementService.markAsRead(a.announcementId).subscribe(() => {
      a.read = true;
    });
  }

  acknowledge(a: any) {
    this.announcementService.markAsAcknowledged(a.announcementId).subscribe(() => {
      a.acknowledged = true;
    });
  }
}
