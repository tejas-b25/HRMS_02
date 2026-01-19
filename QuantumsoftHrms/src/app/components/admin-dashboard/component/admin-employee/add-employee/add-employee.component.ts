import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { EmployeePayload, EmployeeService } from '../../../../../services/employee.service';
// import { EmployeeService, EmployeePayload } from '../services/employee.service'; // adjust path if needed

@Component({
  selector: 'app-add-employee',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent {

  // Form model
  formData: any = {
    firstName: '',
    lastName: '',
    username: '',
    password: '',
    dateOfBirth: '',
    gender: '',
    contactNumber: '',
    email: '',
    address: '',
    bloodGroup: '',
    dateOfJoining: '',
    designation: '',
    department: '',
    employmentType: 'FULL_TIME',  // default
    role: 'EMPLOYEE',             // default
    status: 'ACTIVE',

    reportingManagerId: null as number | null,
    hrbpId: null as number | null
  };

  // Dropdown options (change values to match your enums/tables)
  genders = ['Male', 'Female', 'Other'];

  employmentTypes = [
    'FULL_TIME',
    'PART_TIME',
    'CONTRACT',
    'INTERN'
  ];

  roles = [
    'ADMIN',
    'HR',
    'MANAGER',
    'EMPLOYEE'
  ];

  departments = [
    'IT',
    'HR',
    'Finance',
    'Sales',
    'Operations'
  ];

  designations = [
    'Software Engineer',
    'Senior Software Engineer',
    'Team Lead',
    'HR Executive',
    'Manager'
  ];

  isSubmitting = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  onSubmit(form: NgForm) {
    if (form.invalid || !this.formData.reportingManagerId || !this.formData.hrbpId) {
      this.errorMessage = 'Please fill all required fields including Reporting Manager and HRBP.';
      this.successMessage = '';
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    const payload: EmployeePayload = {
      firstName: this.formData.firstName.trim(),
      lastName: this.formData.lastName.trim(),
      username: this.formData.username.trim(),
      password: this.formData.password,
      dateOfBirth: this.formData.dateOfBirth,   // yyyy-MM-dd
      gender: this.formData.gender,
      contactNumber: this.formData.contactNumber.trim(),
      email: this.formData.email.trim(),
      address: this.formData.address.trim(),
      bloodGroup: this.formData.bloodGroup || '',
      dateOfJoining: this.formData.dateOfJoining, // yyyy-MM-dd
      designation: this.formData.designation,
      department: this.formData.department,
      employmentType: this.formData.employmentType,
      role: this.formData.role,
      status: this.formData.status,

      reportingManager: { id: this.formData.reportingManagerId },
      hrbp: { id: this.formData.hrbpId }
    };

    this.employeeService.createEmployee(payload).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.successMessage = 'Employee created successfully.';
        this.errorMessage = '';

        // Reset form with some defaults
        form.resetForm({
          employmentType: 'FULL_TIME',
          role: 'EMPLOYEE',
          status: 'ACTIVE'
        });
      },
      error: (err) => {
        this.isSubmitting = false;
        this.successMessage = '';
        this.errorMessage = err?.message || 'Failed to create employee.';
      }
    });
  }
onBack() {
  // Go back to employee list under admin layout
  this.router.navigate(['/admin/employee/employee-list']);
}
}
