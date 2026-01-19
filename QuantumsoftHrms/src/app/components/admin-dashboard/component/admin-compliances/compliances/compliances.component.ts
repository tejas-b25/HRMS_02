import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Compliance, ComplianceService } from '../../../../../services/compliances.service';

@Component({
  selector: 'app-compliance',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './compliances.component.html',
  styleUrls: ['./compliances.component.css']
})
export class ComplianceComponent implements OnInit {

  complianceForm!: FormGroup;
  compliances: Compliance[] = [];
  loading = true;

  isEditMode = false;
  editId: number | null = null;

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
      complianceName: ['', Validators.required],
      complianceType: ['', Validators.required],
      description: ['', Validators.required],
      isActive: [true],
      createdBy: ['admin'],
      updatedBy: ['admin']
    });
  }

  loadCompliances() {
    this.loading = true;

    this.complianceService.getAllCompliances().subscribe({
      next: (res) => this.compliances = res,
      complete: () => this.loading = false
    });
  }

  submitForm() {
  if (this.complianceForm.invalid) {
    this.complianceForm.markAllAsTouched();
    return;
  }

  const payload: Compliance = this.complianceForm.value;

  if (this.isEditMode && this.editId !== null) {
    // UPDATE
    this.complianceService.updateCompliance(this.editId, payload).subscribe({
      next: (res) => {
        console.log("Update Success:", res);
        this.isEditMode = false;
        this.editId = null;
        this.loadCompliances();
        this.resetForm();
      },
      error: (err) => {
        console.error("Update Error:", err);
      }
    });

  } else {
    // CREATE
    this.complianceService.createCompliance(payload).subscribe({
      next: (res) => {
        console.log("Saved Successfully:", res);  // 👈 HERE
        this.loadCompliances();
        this.resetForm();
      },
      error: (err) => {
        console.error("Save Error:", err);        // 👈 HERE
      }
    });
  }
}

  

  resetForm() {
    this.complianceForm.reset({
      complianceName: '',
      complianceType: '',
      description: '',
      isActive: true,
      createdBy: 'admin',
      updatedBy: 'admin'
    });
  }

  editCompliance(c: Compliance) {
    this.isEditMode = true;
    this.editId = c.complianceId!;

    this.complianceForm.patchValue({
      complianceName: c.complianceName,
      complianceType: c.complianceType,
      description: c.description,
      isActive: c.isActive,
      createdBy: 'admin',
      updatedBy: 'admin'
    });

    // optional remove from list
    this.compliances = this.compliances.filter(x => x.complianceId !== c.complianceId);
  }

  deleteCompliance(id: number) {
    if (!confirm("Are you sure you want to delete this compliance?")) return;

    this.complianceService.deleteCompliance(id).subscribe(() => {
      this.compliances = this.compliances.filter(c => c.complianceId !== id);
    });
  }



  
}
