import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { LeaveType, LeaveTypeService } from '../../../../../services/leave-type.service';

@Component({
  selector: 'app-leave-type',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './leave-type.component.html',
  styleUrls: ['./leave-type.component.css']
})
export class LeaveTypeComponent implements OnInit {

  leaveTypeForm!: FormGroup;
  leaveTypes: LeaveType[] = [];

  isEditMode = false;
  selectedLeaveTypeId: number | null = null;

  isLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private leaveTypeService: LeaveTypeService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadLeaveTypes();
  }

  private initForm(): void {
    this.leaveTypeForm = this.fb.group({
      name: ['', [
        Validators.required,
        Validators.pattern(/^[a-zA-Z ]+$/)
      ]],
      description: [''],
      annualLimit: [null, [
        Validators.required,
        Validators.min(0)
      ]],
      carryForward: [false],
      encashable: [false],
      isActive: [true]
    });
  }

  loadLeaveTypes(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.leaveTypeService.getAllLeaveTypes().subscribe({
      next: data => {
        this.leaveTypes = data;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Failed to load leave types.';
        this.isLoading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.leaveTypeForm.invalid) {
      this.leaveTypeForm.markAllAsTouched();
      return;
    }

    const payload = this.leaveTypeForm.value as LeaveType;
    this.errorMessage = '';
    this.successMessage = '';

    if (this.isEditMode && this.selectedLeaveTypeId !== null) {
      this.leaveTypeService.updateLeaveType(this.selectedLeaveTypeId, payload).subscribe({
        next: () => {
          this.successMessage = 'Leave Type updated successfully.';
          this.resetForm();
          this.loadLeaveTypes();
        },
        error: () => {
          this.errorMessage = 'Failed to update leave type.';
        }
      });
    } else {
      this.leaveTypeService.createLeaveType(payload).subscribe({
        next: () => {
          this.successMessage = 'Leave Type created successfully.';
          this.resetForm();
          this.loadLeaveTypes();
        },
        error: err => {
          this.errorMessage = typeof err.error === 'string'
            ? err.error
            : 'Failed to create leave type.';
        }
      });
    }
  }

  editLeaveType(leaveType: LeaveType): void {
    this.isEditMode = true;
    this.selectedLeaveTypeId = leaveType.leaveTypeId ?? null;

    this.leaveTypeForm.patchValue({
      name: leaveType.name,
      description: leaveType.description,
      annualLimit: leaveType.annualLimit,
      carryForward: leaveType.carryForward,
      encashable: leaveType.encashable,
      isActive: leaveType.isActive
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteLeaveType(leaveType: LeaveType): void {
    if (!leaveType.leaveTypeId) return;

    if (!confirm(`Delete leave type "${leaveType.name}"?`)) return;

    this.leaveTypeService.deleteLeaveType(leaveType.leaveTypeId).subscribe({
      next: () => {
        this.successMessage = 'Leave Type deleted successfully.';
        this.loadLeaveTypes();
      },
      error: () => {
        this.errorMessage = 'Failed to delete leave type.';
      }
    });
  }

  cancelEdit(): void {
    this.resetForm();
  }

  private resetForm(): void {
    this.leaveTypeForm.reset({
      name: '',
      description: '',
      annualLimit: null,
      carryForward: false,
      encashable: false,
      isActive: true
    });
    this.isEditMode = false;
    this.selectedLeaveTypeId = null;
  }

  get name() {
    return this.leaveTypeForm.get('name');
  }

  get annualLimit() {
    return this.leaveTypeForm.get('annualLimit');
  }
}
