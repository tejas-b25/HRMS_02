import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-navbar',
  standalone: true,
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
// export class NavbarComponent {
//   @Input() currentPageTitle = '';
export class NavbarComponent {
  @Input() pageTitle: string = '';
// }
  logout() {
    // Implement your logout logic here
    console.log('User logged out');
  }
}
