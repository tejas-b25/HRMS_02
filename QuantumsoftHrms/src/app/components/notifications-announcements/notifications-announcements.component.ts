import { Component } from '@angular/core';

@Component({
  selector: 'app-notifications-announcements',
  standalone: true,
  template: `<h2>Notifications & Announcements</h2><p>View notifications and announcements here.</p>`
})
export class NotificationsAnnouncementsComponent {
  notifications = ['System update at 5 PM', 'New policy released'];
}
