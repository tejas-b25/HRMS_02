import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { EmployeePayload, EmployeeService } from '../../../../../services/employee.service';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  employees: EmployeePayload[] = [];
  filteredEmployees: EmployeePayload[] = [];

  loading = false;
  errorMessage = '';
  searchTerm = '';

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchEmployees();
  }

  fetchEmployees() {
    this.loading = true;
    this.errorMessage = '';

    this.employeeService.getEmployees().subscribe({
      next: (data) => {
        this.employees = data;
        this.filteredEmployees = data;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err?.message || 'Failed to load employees.';
      }
    });
  }

  onSearch() {
    const search = this.searchTerm.trim().toLowerCase();

    if (!search) {
      this.filteredEmployees = this.employees;
      return;
    }

    this.filteredEmployees = this.employees.filter(emp => {
      const fullName = (emp.firstName + ' ' + (emp.lastName || '')).toLowerCase();

      return (
        fullName.includes(search) ||
        emp.email?.toLowerCase().includes(search) ||
        emp.department?.toLowerCase().includes(search) ||
        emp.designation?.toLowerCase().includes(search)
      );
    });
  }

  addEmployee() {
    this.router.navigate(['/admin/employee/add-employee']);
  }

  editEmployee(emp: EmployeePayload) {
    if (!emp.employeeId) {
      alert('Invalid employee id');
      return;
    }
    this.router.navigate(['/admin/employee/edit-employee', emp.employeeId]);
  }

  deleteEmployee(emp: EmployeePayload) {
    if (!emp.employeeId) {
      alert('Invalid employee id');
      return;
    }

    if (!confirm('Are you sure you want to delete this employee?')) return;

    this.employeeService.deleteEmployee(emp.employeeId).subscribe({
      next: () => {
        alert('Employee deleted successfully');
        this.fetchEmployees();
      },
      error: (err) => {
        alert(err?.message || 'Error deleting employee.');
      }
    });
  }
}
