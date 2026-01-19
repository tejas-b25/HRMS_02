import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ComplianceService } from '../../../../../services/compliances.service';

@Component({
  selector: 'app-compliance-mapping',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './compliances-mapping.component.html',
  styleUrls: ['./compliances-mapping.component.css']
})
export class CompliancesMappingComponent implements OnInit {

  complianceForm!: FormGroup;
  compliances: any[] = [];
  employeeCompliances: any[] = [];

  isSubmitting = false;
  message: string | null = null;

  constructor(
    private fb: FormBuilder,
    private complianceService: ComplianceService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadCompliances();
  }

  initForm() {
    this.complianceForm = this.fb.group({
      employeeId: ['', Validators.required],
      complianceId: ['', Validators.required],
      complianceNumber: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      remarks: ['']
    });
  }

  loadCompliances() {
    this.complianceService.getAllCompliances().subscribe({
      next: res => this.compliances = res,
      error: () => this.message = 'Failed to load compliances'
    });
  }

  submit() {
    if (this.complianceForm.invalid) {
      this.complianceForm.markAllAsTouched();
      return;
    }

    const payload = {
      ...this.complianceForm.value,
      createdBy: 'hruser'
    };

    this.isSubmitting = true;
    this.complianceService.assignCompliance(payload).subscribe({
      next: () => {
        this.message = 'Compliance assigned successfully';
        this.loadEmployeeCompliances(payload.employeeId);
        this.complianceForm.reset();
        this.isSubmitting = false;
      },
      error: err => {
        this.message = err.error?.message || 'Assignment failed';
        this.isSubmitting = false;
      }
    });
  }

  loadEmployeeCompliances(employeeId: number) {
    this.complianceService.getEmployeeCompliances(employeeId).subscribe({
      next: res => this.employeeCompliances = res,
      error: () => this.message = 'Failed to load employee compliances'
    });
  }
}