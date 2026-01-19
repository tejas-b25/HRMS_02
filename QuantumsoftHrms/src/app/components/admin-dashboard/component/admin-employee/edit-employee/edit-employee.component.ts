import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeePayload, EmployeeService } from '../../../../../services/employee.service';
@Component({
  selector: 'app-edit-employee',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.css']
})
export class EditEmployeeComponent implements OnInit {

  employeeId!: number;
  formData: any = {};

  loading = false;
  saving = false;
  errorMessage = '';
  successMessage = '';

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

  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const routeId = this.route.snapshot.paramMap.get('id');
    this.employeeId = Number(routeId);
    console.log('EditEmployeeComponent id =', this.employeeId);

    if (this.employeeId) {
      this.loadEmployee();
    } else {
      this.errorMessage = 'Invalid employee id in URL';
    }
  }

  loadEmployee() {
    this.loading = true;
    this.errorMessage = '';

    this.employeeService.getEmployeeById(this.employeeId).subscribe({
      next: (emp) => {
        this.loading = false;

        this.formData = {
          ...emp,
          reportingManagerId: emp.reportingManager?.id,
          hrbpId: emp.hrbp?.id
        };
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err?.message || 'Failed to load employee.';
      }
    });
  }

  onSubmit(form: NgForm) {
    if (form.invalid) {
      this.errorMessage = 'Please fill all required fields.';
      this.successMessage = '';
      return;
    }

    if (!this.formData.reportingManagerId || !this.formData.hrbpId) {
      this.errorMessage = 'Reporting Manager and HRBP are required.';
      this.successMessage = '';
      return;
    }

    this.saving = true;
    this.errorMessage = '';
    this.successMessage = '';

    const payload: EmployeePayload = {
      id: this.employeeId,
      employeeId: this.employeeId, // in case backend expects employeeId
      firstName: this.formData.firstName.trim(),
      lastName: this.formData.lastName?.trim() || '',
      username: this.formData.username.trim(),
      password: this.formData.password, // keep same password
      dateOfBirth: this.formData.dateOfBirth,
      gender: this.formData.gender,
      contactNumber: this.formData.contactNumber.trim(),
      email: this.formData.email.trim(),
      address: this.formData.address || '',
      bloodGroup: this.formData.bloodGroup || '',
      dateOfJoining: this.formData.dateOfJoining,
      designation: this.formData.designation,
      department: this.formData.department,
      employmentType: this.formData.employmentType,
      role: this.formData.role,
      status: this.formData.status,
      reportingManager: { id: this.formData.reportingManagerId },
      hrbp: { id: this.formData.hrbpId }
    };

    this.employeeService.updateEmployee(this.employeeId, payload).subscribe({
     next: () => {
    this.saving = false;
    this.successMessage = 'Employee updated successfully.';
    this.errorMessage = '';

    setTimeout(() => {
      this.router.navigate(['/admin/employee/employee-list']);  // ✅ MATCHES ROUTES
    }, 800);
  },
    });
  }

  onBack() {
  // OLD: this.router.navigate(['/admin/admin-emp-list']);
  this.router.navigate(['/admin/employee/employee-list']);
}
}
