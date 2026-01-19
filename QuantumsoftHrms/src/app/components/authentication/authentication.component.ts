import { CommonModule } from '@angular/common';
import { jwtDecode } from "jwt-decode";
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink, RouterModule } from "@angular/router";
import { AuthService } from '../../services/auth.service';
@Component({
  selector: 'app-authentication',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink, RouterModule],
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {
 
  @ViewChild('signupForm') signupForm!: NgForm;
  @ViewChild('signinForm') signinForm!: NgForm;
 
  constructor(private authService: AuthService,private router: Router) { }
 
  ngOnInit(): void {
    // Check if user is already logged in
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.redirectBasedOnRole();
    }
  }
 
  rightPanelActive = false;
  toggleSignUp() {
    this.rightPanelActive = true;
  }
 
  toggleSignIn() {
    this.rightPanelActive = false;
  }
 
  // Signup object
  signupObj: any = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    username: ''
  };
 
  // Login object
  loginObj: any = {
    username: '',
    password: ''
  };
 
  activeLogin: 'user' | 'admin' | null = null;
  isLoading = false;
  errorMessage = '';
 
  // Signup Method (Local Storage)
 onSignup() {
  if (!this.signupObj.firstName || !this.signupObj.email || !this.signupObj.password) {
    alert("First Name, Email and Password are required!");
    return;
  }
 
  this.isLoading = true;
  this.authService.signup(this.signupObj).subscribe({
    next: (res: any) => {
      this.isLoading = false;
      alert('Account created successfully! Please login.');
      this.signupForm.resetForm();
      this.toggleSignIn();
    },
    error: (err) => {
      this.isLoading = false;
      this.errorMessage = err.error?.message || 'Signup failed!';
    }
  });
}
 
  // User Login (Local Storage)
onuserLogin() {
  if (!this.loginObj.username || !this.loginObj.password) {
    alert("Username and Password are required!");
    return;
  }

  this.isLoading = true;

  this.authService.userLogin(this.loginObj).subscribe({
    next: (res: any) => {
      console.log("USER LOGIN RESPONSE:", res);

      this.isLoading = false;

      if (res?.token) {
        const decoded: any = jwtDecode(res.token);
        console.log("Decoded Token:", decoded);

        const role = decoded.role || decoded.roles || decoded.authorities || null;

        if (!role) {
          alert("Role not found in token!");
          return;
        }

        // ✅ Allow only HR & Employee
        if (role !== 'ROLE_EMPLOYEE' && role !== 'ROLE_HR') {
          alert("Access denied! Only HR or Employee can login here.");
          return;
        }

        localStorage.setItem('accessToken', res.token);
        localStorage.setItem('userRole', role);

        alert("Login successful!");
        this.router.navigate(['/app/home']);
      } else {
        alert("Invalid response from server.");
      }
    },
    error: (err) => {
      this.isLoading = false;
      alert(err.error?.message || "Invalid user credentials!");
    }
  });
}


 
 
 
  // Admin Login (Local Storage)
onadminLogin() {
  if (!this.loginObj.username || !this.loginObj.password) {
    alert("Username and Password are required!");
    return;
  }

  this.isLoading = true;

  this.authService.adminLogin(this.loginObj).subscribe({
    next: (res: any) => {
      console.log("ADMIN LOGIN RESPONSE:", res);

      this.isLoading = false;

      if (res?.token) {
        const decoded: any = jwtDecode(res.token);

        const role = decoded.role || decoded.roles || decoded.authorities || null;

        if (!role) {
          alert("Role not found in token!");
          return;
        }

        // ✅ Allow only Admin & Superadmin
        if (role !== 'ROLE_ADMIN' && role !== 'ROLE_SUPERADMIN') {
          alert("Access denied! Only Admin or Superadmin can login here.");
          return;
        }

        localStorage.setItem('accessToken', res.token);
        localStorage.setItem('userRole', role);

        alert("Admin login successful!");
        this.router.navigate(['/admin_dashboard']);
      } else {
        alert("Invalid response from server.");
      }
    },
    error: (err) => {
      this.isLoading = false;
      alert(err.error?.message || "Invalid admin credentials!");
    }
  });
}

 
 
  // Redirect based on user role
  private redirectBasedOnRole() {
    const userRole = localStorage.getItem('userRole') || 'ROLE_USER';
   
    switch (userRole) {
      case 'ROLE_SUPERADMIN':
      case 'ROLE_ADMIN':
        this.router.navigate(['/dashboard']);
        break;
      case 'ROLE_HR':
        this.router.navigate(['/people']);
        break;
      case 'ROLE_MANAGER':
        this.router.navigate(['/myteam']);
        break;
      case 'ROLE_FINANCE':
        this.router.navigate(['/reports-analytics']);
        break;
      case 'ROLE_USER':
      case 'ROLE_EMPLOYEE':
        this.router.navigate(['/home']);
        break;
      default:
        this.router.navigate(['/home']);
    }
  }
 
  // Initialize demo users in localStorage
  // private initializeDemoUsers() {
  //   const demoUsers = [
  //     {
  //       id: 1,
  //       firstName: 'Super',
  //       lastName: 'Admin',
  //       email: 'superadmin@demo.com',
  //       username: 'superadmin',
  //       password: 'superadmin123',
  //       role: 'ROLE_SUPERADMIN'
  //     },
  //     {
  //       id: 2,
  //       firstName: 'Admin',
  //       lastName: 'User',
  //       email: 'admin@demo.com',
  //       username: 'admin',
  //       password: 'admin123',
  //       role: 'ROLE_ADMIN'
  //     },
  //     {
  //       id: 3,
  //       firstName: 'HR',
  //       lastName: 'Manager',
  //       email: 'hr@demo.com',
  //       username: 'hrAdmin',
  //       password: 'hrAdmin123',
  //       role: 'ROLE_HR'
  //     },
  //     {
  //       id: 4,
  //       firstName: 'John',
  //       lastName: 'Doe',
  //       email: 'employee@demo.com',
  //       username: 'new2',
  //       password: 'new2123',
  //       role: 'ROLE_EMPLOYEE'
  //     }
  //   ];
 
  //   // Only initialize if no users exist
  //   const existingUsers = localStorage.getItem('users');
  //   if (!existingUsers) {
  //     localStorage.setItem('users', JSON.stringify(demoUsers));
  //   }
  // }
 
  // Demo credentials for testing
  // fillDemoCredentials(role: string): void {
  //   this.initializeDemoUsers(); // Ensure demo users exist
   
  //   const demos: { [key: string]: any } = {
  //     superadmin: { username: 'superadmin', password: 'superadmin123' },
  //     admin: { username: 'admin', password: 'admin123' },
  //     hr: { username: 'hrAdmin', password: 'hrAdmin123' },
  //     employee: { username: 'new2', password: 'new2123' }
  //   };
 
  //   this.loginObj = demos[role] || { username: '', password: '' };
  //   this.errorMessage = '';
  // }
 
  allowOnlyLetters(event: KeyboardEvent) {
    const pattern = /^[A-Za-z]$/;
    if (!pattern.test(event.key)) {
      event.preventDefault();
    }
  }
 
  showUserLogin() {
    this.activeLogin = 'user';
  }
 
  showAdminLogin() {
    this.activeLogin = 'admin';
  }
 
  resetLogin() {
    this.activeLogin = null;
    this.loginObj = { username: '', password: '' };
    this.errorMessage = '';
  }
 
 
 
 
 
}