import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Benefit, BenefitService } from '../../../../../services/benefits.service';

@Component({
  selector: 'app-benefit-master',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './benefit-master.component.html',
  styleUrl: './benefit-master.component.css'
})
export class BenefitMasterComponent implements OnInit {

  benefitForm!: FormGroup;
  benefits: Benefit[] = [];
  loading = true;

  isEditMode = false;
  editId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private benefitService: BenefitService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadBenefits();
  }

  initForm() {
    this.benefitForm = this.fb.group({
      benefitName: ['', [
        Validators.required,
        Validators.pattern(/^[a-zA-Z ]+$/)
      ]],
      benefitType: ['', Validators.required],
      description: ['', [
        Validators.required,
        Validators.minLength(10)
      ]],
      isActive: [false],
      createdBy: ['HR'],
      updatedBy: ['Admin']
    });
  }

  loadBenefits() {
    this.loading = true;
    this.benefitService.getAllBenefits().subscribe({
      next: res => this.benefits = res,
      complete: () => this.loading = false
    });
  }

  submitForm() {
    if (this.benefitForm.invalid) {
      this.benefitForm.markAllAsTouched();
      return;
    }

    const payload = this.benefitForm.value as Benefit;

    if (this.isEditMode && this.editId !== null) {
      this.benefitService.updateBenefit(this.editId, payload).subscribe(() => {
        this.afterSave();
      });
    } else {
      this.benefitService.createBenefit(payload).subscribe(() => {
        this.afterSave();
      });
    }
  }

  afterSave() {
    this.resetForm();
    this.loadBenefits();
  }

  resetForm() {
    this.benefitForm.reset({
      benefitName: '',
      benefitType: '',
      description: '',
      isActive: false,
      createdBy: 'HR',
      updatedBy: 'Admin'
    });
    this.isEditMode = false;
    this.editId = null;
  }

  editBenefit(b: Benefit) {
    if (!b.benefitId) return;

    this.isEditMode = true;
    this.editId = b.benefitId;

    this.benefitForm.patchValue({
      benefitName: b.benefitName,
      benefitType: b.benefitType,
      description: b.description,
      isActive: b.isActive,
      createdBy: b.createdBy,
      updatedBy: b.updatedBy
    });
  }

  deleteBenefit(id?: number) {
    if (!id) return;
    if (!confirm('Delete this benefit?')) return;

    this.benefitService.deleteBenefit(id).subscribe(() => {
      this.benefits = this.benefits.filter(b => b.benefitId !== id);
    });
  }
}
