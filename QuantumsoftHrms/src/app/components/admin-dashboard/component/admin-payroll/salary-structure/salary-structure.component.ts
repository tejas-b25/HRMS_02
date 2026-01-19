import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PayrollStructure, PayrollStructureService } from '../../../../../services/payroll-structure.service';

@Component({
  selector: 'app-salary-structure',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './salary-structure.component.html',
  styleUrls: ['./salary-structure.component.css']
})
export class SalaryStructureComponent implements OnInit {

  structureForm!: FormGroup;
  message = '';
  error = '';

  constructor(
    private fb: FormBuilder,
    private payrollService: PayrollStructureService
  ) {}

  ngOnInit(): void {
    this.structureForm = this.fb.group({
      structureId: [null],
      structureName: ['', [Validators.required, Validators.pattern(/^[a-zA-Z ]+$/)]],
      basicPay: [0, [Validators.required, Validators.min(0)]],
      hra: [0, [Validators.required, Validators.min(0)]],
      variablePay: [0, [Validators.required, Validators.min(0)]],
      otherAllowances: [0, [Validators.required, Validators.min(0)]],
      deductions: [0, [Validators.required, Validators.min(0)]]
    });
  }

  get structureNameCtrl() { return this.structureForm.get('structureName'); }
  get basicPayCtrl() { return this.structureForm.get('basicPay'); }
  get hraCtrl() { return this.structureForm.get('hra'); }
  get variablePayCtrl() { return this.structureForm.get('variablePay'); }
  get otherAllowancesCtrl() { return this.structureForm.get('otherAllowances'); }
  get deductionsCtrl() { return this.structureForm.get('deductions'); }

  get grossSalary(): number {
    const v = this.structureForm.value;
    return Number(v.basicPay) + Number(v.hra) + Number(v.variablePay) + Number(v.otherAllowances);
  }

  get netSalary(): number {
    return this.grossSalary - Number(this.structureForm.value.deductions);
  }

  submitForm() {
    if (this.structureForm.invalid) {
      this.structureForm.markAllAsTouched();
      return;
    }

    const payload = this.structureForm.value as PayrollStructure;

    this.payrollService.saveStructure(payload).subscribe({
      next: res => {
        this.message = 'Salary structure saved successfully';
        if (res?.structureId) {
          this.structureForm.patchValue({ structureId: res.structureId });
        }
      },
      error: err => {
        this.error = err?.error?.message || 'Failed to save structure';
      }
    });
  }

  resetForm() {
    this.structureForm.reset({
      structureId: null,
      structureName: '',
      basicPay: 0,
      hra: 0,
      variablePay: 0,
      otherAllowances: 0,
      deductions: 0
    });
    this.message = '';
    this.error = '';
  }
}
