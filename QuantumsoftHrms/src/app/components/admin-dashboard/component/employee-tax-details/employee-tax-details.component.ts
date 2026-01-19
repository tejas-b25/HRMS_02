import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { EmployeeTaxService } from '../../../../services/employee-tax.service';

@Component({
  selector: 'app-employee-tax-details',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './employee-tax-details.component.html',
  styleUrls: ['./employee-tax-details.component.css']
})
export class EmployeeTaxDetailsComponent {

  taxForm: FormGroup;
  taxList: any[] = [];

  loading = false;
  successMsg = '';
  errorMsg = '';

  constructor(
    private fb: FormBuilder,
    private taxService: EmployeeTaxService
  ) {
    this.taxForm = this.fb.group({
      employeeId: ['', Validators.required],
      taxConfigId: ['', Validators.required],
      taxRegime: ['', Validators.required],
      declarationText: ['', Validators.required]
    });
  }

  submit() {
    if (this.taxForm.invalid) {
      this.taxForm.markAllAsTouched();
      return;
    }

    const payload = {
      employeeId: this.taxForm.value.employeeId,
      taxConfiguration: {
        taxConfigId: this.taxForm.value.taxConfigId
      },
      taxRegime: this.taxForm.value.taxRegime,
      declarationText: this.taxForm.value.declarationText
    };

    this.loading = true;
    this.successMsg = '';
    this.errorMsg = '';

    this.taxService.saveTax(payload).subscribe({
      next: () => {
        this.loading = false;
        this.successMsg = 'Tax details saved successfully';
        this.loadTax(); 
      },
      error: () => {
        this.loading = false;
        this.errorMsg = 'Failed to save tax details';
      }
    });
  }

  loadTax() {
    const empId = this.taxForm.value.employeeId;
    if (!empId) return;

    this.taxService.getTaxByEmployee(empId).subscribe({
      next: (res) => this.taxList = res,
      error: () => this.errorMsg = 'Failed to load tax details'
    });
  }


  
}
