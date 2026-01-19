import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'QuantumsoftHrms';
  isLoggedIn: boolean = false;

  constructor(private router: Router) {
    
       this.router.navigate(['/']);     // here is change with a path   {"/app"== "/"}
    this.checkLoginStatus();

    // Listen for changes in localStorage (e.g., login/logout from another tab)
    window.addEventListener('storage', this.checkLoginStatus.bind(this));
  }

  /**
   * Check login status based on localStorage keys
   */
  checkLoginStatus() {
    const user = localStorage.getItem('loggedInUser');
    const admin = localStorage.getItem('loggedInAdmin');
    this.isLoggedIn = !!(user || admin);
  }

  /**
   * Watch for route changes and enable/disable browser navigation accordingly
   */
  ngOnInit() {
    this.router.events
      .pipe(
        // ✅ Proper type guard for NavigationEnd
        filter((event): event is NavigationEnd => event instanceof NavigationEnd)
      )
      .subscribe((event: NavigationEnd) => {
        const currentUrl = event.urlAfterRedirects;

        // 👇 All routes where navigation should be disabled
        const authRoutes = ['/', '/authentication', '/authentication/forget', '/login', '/signup', '/auth'];

        if (authRoutes.includes(currentUrl)) {
          // 🔒 Disable browser back/forward buttons
          this.disableBrowserNavigation();
        } else {
          // 🔓 Enable browser navigation on other pages
          this.enableBrowserNavigation();
        }
      });
  }

  /**
   * Prevent user from navigating back or forward in the browser
   */
  disableBrowserNavigation() {
    window.history.pushState(null, '', window.location.href);
    window.onpopstate = function () {
      window.history.go(1);
    };
  }

  /**
   * Restore normal browser navigation
   */
  enableBrowserNavigation() {
    window.onpopstate = null;
  }
}
