import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmployeeBankService, EmployeeBankDetails } from '../../../../../services/employee-bank.service';
import { NgIf, NgFor } from '@angular/common';

@Component({
  selector: 'app-banking',
  standalone: true,
  imports: [CommonModule, FormsModule,ReactiveFormsModule],
  templateUrl: './banking.component.html',
  styleUrls: ['./banking.component.css']
})
export class BankingComponent implements OnInit {

  bankForm!: FormGroup;
  loading = false;
  message = '';
  error = '';

  // loaded bank details (from GET) - shown in UI
  loadedBank?: EmployeeBankDetails | null = null;

  // optional: control to load by employee id quickly
  loadEmployeeId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private bankSvc: EmployeeBankService
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.bankForm = this.fb.group({
      id: [null], // optional DB id returned by backend
      employeeId: [null, [Validators.required, Validators.min(1)]],
      bankName: ['', [Validators.required]],
      accountHolderName: ['', [Validators.required]],
      accountNumber: ['', [Validators.required]],
      accountType: ['Savings', [Validators.required]],
      ifsc: ['', [Validators.required]],
      branch: ['', [Validators.required]],
      uanNumber: ['']
    });
  }

  // getters for template
  get employeeIdCtrl() { return this.bankForm.get('employeeId'); }
  get bankNameCtrl() { return this.bankForm.get('bankName'); }
  get accountHolderCtrl() { return this.bankForm.get('accountHolderName'); }
  get accountNumberCtrl() { return this.bankForm.get('accountNumber'); }
  get ifscCtrl() { return this.bankForm.get('ifsc'); }
  get branchCtrl() { return this.bankForm.get('branch'); }

  // Save (POST)
  submit() {
    this.message = '';
    this.error = '';

    if (this.bankForm.invalid) {
      this.bankForm.markAllAsTouched();
      return;
    }

    const payload = this.bankForm.value as EmployeeBankDetails;

    this.loading = true;
    this.bankSvc.saveBankDetails(payload).subscribe({
      next: (res) => {
        this.loading = false;
        this.message = 'Bank details saved successfully.';
        this.loadedBank = res;
        // If backend returns id, patch it into form for later update/delete
        if (res && (res as any).id) {
          this.bankForm.patchValue({ id: (res as any).id });
        }
      },
      error: (err) => {
        console.error('Save error', err);
        this.loading = false;
        this.error = err?.error?.message || 'Failed to save bank details.';
      }
    });
  }

  // Load by employee id (GET)
  loadByEmployeeId() {
    this.message = '';
    this.error = '';
    this.loadedBank = null;

    if (this.loadEmployeeId == null || isNaN(this.loadEmployeeId)) {
      this.error = 'Enter a valid employee id to load.';
      return;
    }

    this.loading = true;
    this.bankSvc.getByEmployeeId(this.loadEmployeeId).subscribe({
      next: (res) => {
        this.loading = false;
        this.loadedBank = res;
        // patch into form so user can update if needed
        this.bankForm.patchValue({
          id: (res as any).id ?? null,
          employeeId: res.employeeId,
          bankName: res.bankName,
          accountHolderName: res.accountHolderName,
          accountNumber: res.accountNumber,
          accountType: res.accountType,
          ifsc: res.ifsc,
          branch: res.branch,
          uanNumber: res.uanNumber
        });
      },
      error: (err) => {
        console.error('Load error', err);
        this.loading = false;
        this.error = err?.error?.message || 'No bank details found for this employee.';
      }
    });
  }

  // Update (if backend supports PUT /{id})
  update() {
    this.message = '';
    this.error = '';

    const id = this.bankForm.value.id;
    if (!id) {
      this.error = 'No record id available to update. Save first or load a record.';
      return;
    }

    if (this.bankForm.invalid) {
      this.bankForm.markAllAsTouched();
      return;
    }

    const payload = this.bankForm.value as EmployeeBankDetails;

    this.loading = true;
    this.bankSvc.updateBankDetails(id, payload).subscribe({
      next: (res) => {
        this.loading = false;
        this.message = 'Bank details updated successfully.';
        this.loadedBank = res;
      },
      error: (err) => {
        console.error('Update error', err);
        this.loading = false;
        this.error = err?.error?.message || 'Failed to update bank details.';
      }
    });
  }

  // Delete
  deleteLoaded() {
    this.message = '';
    this.error = '';

    const id = this.bankForm.value.id ?? (this.loadedBank as any)?.id;
    if (!id) {
      this.error = 'No record id available to delete.';
      return;
    }

    if (!confirm('Are you sure you want to delete the bank details?')) return;

    this.loading = true;
    this.bankSvc.deleteBankDetails(id).subscribe({
      next: (res) => {
        this.loading = false;
        this.message = 'Bank details deleted successfully.';
        this.reset();
      },
      error: (err) => {
        console.error('Delete error', err);
        this.loading = false;
        this.error = err?.error?.message || 'Failed to delete bank details.';
      }
    });
  }

  reset() {
    this.bankForm.reset({
      id: null,
      employeeId: null,
      bankName: '',
      accountHolderName: '',
      accountNumber: '',
      accountType: 'Savings',
      ifsc: '',
      branch: '',
      uanNumber: ''
    });
    this.loadedBank = null;
    this.message = '';
    this.error = '';
    this.loadEmployeeId = null;
  }
}
