import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PayrollProcessRequest, PayrollProcessService } from '../../../../../services/payroll-process.service';

@Component({
  selector: 'app-payroll-processing',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './payroll-processing.component.html',
  styleUrls: ['./payroll-processing.component.css']
})
export class PayrollProcessingComponent implements OnInit {

  processForm!: FormGroup;

  message = '';
  error = '';
  loading = false;

  constructor(
    private fb: FormBuilder,
    private payrollProcessService: PayrollProcessService
  ) {}

  ngOnInit(): void {
    this.processForm = this.fb.group({
      employeeId: [null, [Validators.required, Validators.min(1)]],
      month: ['', [
        Validators.required,
        Validators.pattern(/^[A-Z]{3}-\d{4}$/)
      ]],
      structureId: [null, [Validators.required, Validators.min(1)]],
      bankReferenceNo: ['', [
        Validators.required,
        Validators.pattern(/^[A-Za-z0-9]+$/)
      ]]
    });
  }

  get employeeIdCtrl() { return this.processForm.get('employeeId'); }
  get monthCtrl() { return this.processForm.get('month'); }
  get structureIdCtrl() { return this.processForm.get('structureId'); }
  get bankRefCtrl() { return this.processForm.get('bankReferenceNo'); }

  submit() {
    this.message = '';
    this.error = '';

    if (this.processForm.invalid) {
      this.processForm.markAllAsTouched();
      return;
    }

    const payload = this.processForm.value as PayrollProcessRequest;

    this.loading = true;
    this.payrollProcessService.processPayroll(payload).subscribe({
      next: () => {
        this.loading = false;
        this.message = 'Payroll processed successfully.';
      },
      error: (err) => {
        this.loading = false;
        this.error = err?.error?.message || 'Failed to process payroll.';
      }
    });
  }

  reset() {
    this.processForm.reset();
    this.message = '';
    this.error = '';
  }
}
