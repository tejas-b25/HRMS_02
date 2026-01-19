import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DepartmentService, Department } from '../../../../services/department.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-department',
  standalone: true,
  imports: [FormsModule,ReactiveFormsModule,CommonModule],
    templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {
  deptForm: FormGroup;
  departments: Department[] = [];
  editingDept: Department | null = null;
  errorSummary = '';

  constructor(
    private fb: FormBuilder,
    private deptService: DepartmentService
  ) {
    this.deptForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
      code: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.loadDepartments();
  }

  loadDepartments() {
    this.deptService.getDepartments().subscribe({
      next: data => this.departments = data,
      error: err => this.errorSummary = err.message || 'Failed to load departments.'
    });
  }

  onSave() {
    this.errorSummary = '';
    if (this.deptForm.invalid) {
      this.errorSummary = 'Please fill out the fields correctly.';
      return;
    }

    const formValue = this.deptForm.value;

    if (this.editingDept) {
      const updated: Department = {
        ...this.editingDept,
        name: formValue.name,
        code: formValue.code,
        description: formValue.description
        // status remains unchanged
      };

      this.deptService.updateDepartment(updated).subscribe({
        next: resp => {
          const idx = this.departments.findIndex(d => d.departmentId === resp.departmentId);
          if (idx >= 0) this.departments[idx] = resp;
          alert('Department updated');
          this.resetForm();
        },
        error: err => this.errorSummary = err.message || 'Update failed'
      });
    } else {
      const payload = {
        name: formValue.name,
        code: formValue.code,
        description: formValue.description
      };
      this.deptService.createDepartment(payload).subscribe({
        next: created => {
          this.departments.push(created);
          alert('Department added');
          this.resetForm();
        },
        error: err => this.errorSummary = err.message || 'Creation failed'
      });
    }
  }

  onEdit(dept: Department) {
    this.editingDept = dept;
    this.deptForm.setValue({
      name: dept.name,
      code: dept.code,
      description: dept.description || ''
    });
  }

  onDelete(dept: Department) {
    if (!confirm(`Are you sure you want to deactivate "${dept.name}"?`)) return;

    this.deptService.deleteDepartment(dept.departmentId).subscribe({
      next: () => {
        this.loadDepartments();
        alert('Department deactivated');
      },
      error: err => this.errorSummary = err.message || 'Deactivation failed'
    });
  }

  resetForm() {
    this.editingDept = null;
    this.deptForm.reset();
    this.errorSummary = '';
  }
}
//2