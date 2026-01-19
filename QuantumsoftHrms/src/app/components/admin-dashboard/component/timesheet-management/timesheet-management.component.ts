import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { TimesheetService } from '../../../../services/timesheet.service';

@Component({
  selector: 'app-timesheet-management',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './timesheet-management.component.html',
  styleUrls: ['./timesheet-management.component.css']
})
export class TimesheetManagementComponent {

  timesheetForm: FormGroup;
  timesheetList: any[] = [];

  loading = false;
  successMsg = '';
  errorMsg = '';

  constructor(
    private fb: FormBuilder,
    private timesheetService: TimesheetService
  ) {
    this.timesheetForm = this.fb.group({
      employeeId: ['', Validators.required],
      projectCode: ['', Validators.required],
      taskCode: ['', Validators.required],
      workDate: ['', Validators.required],
      hoursWorked: ['', [Validators.required, Validators.min(0.1)]],
      remarks: ['']
    });
  }

  // SUBMIT & AUTO LOAD
  submit() {
    if (this.timesheetForm.invalid) {
      this.timesheetForm.markAllAsTouched();
      return;
    }

    const payload = {
      employeeId: this.timesheetForm.value.employeeId,
      projectCode: this.timesheetForm.value.projectCode,
      taskCode: this.timesheetForm.value.taskCode,
      workDate: this.timesheetForm.value.workDate,
      hoursWorked: this.timesheetForm.value.hoursWorked,
      remarks: this.timesheetForm.value.remarks
    };

    this.loading = true;
    this.successMsg = '';
    this.errorMsg = '';

    this.timesheetService.submitTimesheet(payload).subscribe({
      next: () => {
        this.loading = false;
        this.successMsg = 'Timesheet submitted successfully';
        this.loadTimesheets(); // ✅ auto refresh
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = err?.error || 'Error submitting timesheet';
      }
    });
  }

  // FETCH TIMESHEETS
  loadTimesheets() {
    const empId = this.timesheetForm.value.employeeId;
    if (!empId) return;

    this.timesheetService.getTimesheetsByEmployee(empId).subscribe({
      next: (res) => this.timesheetList = res,
      error: () => this.errorMsg = 'Failed to load timesheets'
    });
  }
}
