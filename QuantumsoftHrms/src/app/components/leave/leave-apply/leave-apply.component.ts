import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LeaveService } from '../../../services/leave.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-leave-apply',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule],
  templateUrl: './leave-apply.component.html',
  styleUrls: ['./leave-apply.component.css']
})
export class LeaveApplyComponent {

  leaveForm: FormGroup;
  selectedFile: File | null = null;
  loading = false;
  successMsg = '';
  errorMsg = '';

  constructor(
    private fb: FormBuilder,
    private leaveService: LeaveService
  ) {
    this.leaveForm = this.fb.group({
      employeeId: [4, Validators.required],   // you can bind from login later
      leaveTypeId: [3, Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      reason: ['', Validators.required]
    });
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }

  submit() {
    if (this.leaveForm.invalid) {
      this.leaveForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.successMsg = '';
    this.errorMsg = '';

    this.leaveService.applyLeave(this.leaveForm.value, this.selectedFile!)
      .subscribe({
        next: (res) => {
          this.loading = false;
          this.successMsg = 'Leave applied successfully';
          console.log('Response:', res);
          this.leaveForm.reset({
            employeeId: 4,
            leaveTypeId: 3
          });
          this.selectedFile = null;
        },
        error: (err) => {
          this.loading = false;
          this.errorMsg = 'Failed to apply leave';
          console.error(err);
        }
      });
  }
}
