// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
// import { Router } from '@angular/router';
// import { CommonModule } from '@angular/common';
// import { ReactiveFormsModule } from '@angular/forms';
// import { HttpClientModule } from '@angular/common/http';
// import { AuthService } from '../../../../services/auth.service';

// @Component({
//   selector: 'app-register',
//   standalone: true,
//   imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
//   templateUrl: './register.component.html',
//   styleUrls: ['./register.component.css']
// })
// export class RegisterComponent implements OnInit {

//   registerForm: FormGroup;
//   submitted = false;
//   errorSummary = '';

//   users: any[] = [];   // ⭐ STORE USER LIST

//   roles = [
//     { value: 'ADMIN', view: 'Admin' },
//     { value: 'USER', view: 'User' },
//     { value: 'HR',   view: 'HR' },
//     { value: 'EMPLOYEE', view: 'Employee' }
//   ];

//   constructor(
//     private fb: FormBuilder,
//     private router: Router,
//     private auth: AuthService
//   ) {

//     this.registerForm = this.fb.group({
//       username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
//       password: ['', [Validators.required, Validators.minLength(6)]],
//       email: ['', [Validators.required, Validators.email]],
//       firstName: [''],
//       lastName: [''],
//       role: ['', Validators.required]
//     });
//   }

//   ngOnInit(): void {
//     this.loadUsers();
//   }

//   // ⭐ LOAD REGISTERED USERS
//   loadUsers() {
//     this.auth.getAllUsers().subscribe({
//       next: (data) => {
//         this.users = data;
//       },
//       error: (err) => console.error('Error loading users:', err)
//     });
//   }

//   get f(): { [key: string]: AbstractControl } {
//     return this.registerForm.controls;
//   }

//   onSave(): void {
//     this.submitted = true;
//     this.errorSummary = '';

//     if (this.registerForm.invalid) {
//       this.errorSummary = 'Please correct the highlighted fields.';
//       return;
//     }

//     const payload = this.registerForm.value;

//     this.auth.register(payload).subscribe({
//       next: () => {
//         alert('✅ User registered successfully');
//         this.registerForm.reset();
//         this.submitted = false;

//         this.loadUsers();   // ⭐ AUTO REFRESH LIST

//       },
//       error: (err) => {
//         console.error('Register error', err);
//         this.errorSummary = err?.message || 'Registration failed.';
//       }
//     });
//   }

//   onClear() {
//     this.registerForm.reset();
//     this.submitted = false;
//     this.errorSummary = '';
//   }

//   onBack() {
//     this.router.navigate(['/admin/home']);
//   }
// }



import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../../../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,FormsModule, HttpClientModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  submitted = false;
  errorSummary = '';

  // ⭐ user list
  users: any[] = [];

  // ⭐ For editing user
  editingUser: any = null;

  // ⭐ Search
  searchText: string = '';

  roles = [
    { value: 'ADMIN', view: 'Admin' },
    { value: 'USER', view: 'User' },
    { value: 'HR', view: 'HR' },
    { value: 'EMPLOYEE', view: 'Employee' }
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private auth: AuthService
  ) {

    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      firstName: [''],
      lastName: [''],
      role: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  get f(): { [key: string]: AbstractControl } {
    return this.registerForm.controls;
  }

  // ⭐ LOAD USERS
  loadUsers() {
    this.auth.getAllUsers().subscribe({
      next: data => this.users = data,
      error: err => console.error(err)
    });
  }

  // ⭐ SEARCH FILTER
  get filteredUsers() {
    return this.users.filter(u =>
      u.username.toLowerCase().includes(this.searchText.toLowerCase()) ||
      u.email.toLowerCase().includes(this.searchText.toLowerCase()) ||
      u.role.toLowerCase().includes(this.searchText.toLowerCase())
    );
  }

  // ⭐ REGISTER OR UPDATE
  onSave(): void {
    this.submitted = true;
    this.errorSummary = '';

    if (this.registerForm.invalid) {
      this.errorSummary = 'Please correct the highlighted fields.';
      return;
    }

    const payload = this.registerForm.value;

    if (this.editingUser) {
      // update logic if needed later
      alert("Update API not implemented yet.");
      return;
    }

    // CREATE NEW USER
    this.auth.register(payload).subscribe({
      next: () => {
        alert('User registered successfully');
        this.registerForm.reset();
        this.submitted = false;
        this.loadUsers(); // refresh list
      },
      error: (err) => {
        this.errorSummary = err?.message || 'Registration failed.';
      }
    });
  }

  // ⭐ DELETE USER
  onDelete(user: any) {
    if (!confirm(`Delete user "${user.username}"?`)) return;

    this.auth.deleteUser(user.id).subscribe({
      next: () => {
        alert('User deleted');
        this.loadUsers();
      },
      error: err => console.error(err)
    });
  }

  // ⭐ PRE-FILL FOR EDIT USER
  onEdit(user: any) {
    this.editingUser = user;

    this.registerForm.patchValue({
      username: user.username,
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
      role: user.role
    });
  }

  onClear() {
    this.editingUser = null;
    this.registerForm.reset();
    this.submitted = false;
    this.errorSummary = '';
  }

  onBack() {
    this.router.navigate(['/admin/home']);
  }
}
