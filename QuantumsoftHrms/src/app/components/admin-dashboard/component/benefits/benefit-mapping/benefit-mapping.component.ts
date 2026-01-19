import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  EmployeeBenefitMapping,
  EmployeeBenefitMappingService
} from '../../../../../services/employee-benefit-mapping.service';

@Component({
  selector: 'app-benefit-mapping',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './benefit-mapping.component.html',
  styleUrl: './benefit-mapping.component.css'
})
export class BenefitMappingComponent implements OnInit {

  mappings: any[] = [];
  isEditMode = false;

  mapping: EmployeeBenefitMapping = this.resetForm();

  constructor(private service: EmployeeBenefitMappingService) {}

  ngOnInit(): void {
    this.loadMappings();
  }

  resetForm(): EmployeeBenefitMapping {
    return {
      employeeId: 0,
      benefit: { benefitId: 0 },
      provider: { providerId: 0 },
      coverageAmount: 0,
      premiumAmount: 0,
      employerContribution: 0,
      startDate: '',
      endDate: '',
      status: 'ACTIVE'
    };
  }

  loadMappings() {
    this.service.getAll().subscribe(res => this.mappings = res);
  }

  save() {
    if (this.isEditMode && this.mapping.mappingId) {
      this.service.update(this.mapping.mappingId, this.mapping).subscribe(() => {
        this.afterSave();
      });
    } else {
      this.service.create(this.mapping).subscribe(() => {
        this.afterSave();
      });
    }
  }

  edit(m: any) {
    this.mapping = {
      mappingId: m.mappingId,
      employeeId: m.employeeId,
      benefit: { benefitId: m.benefit.benefitId },
      provider: { providerId: m.provider.providerId },
      coverageAmount: m.coverageAmount,
      premiumAmount: m.premiumAmount,
      employerContribution: m.employerContribution,
      startDate: m.startDate,
      endDate: m.endDate,
      status: m.status
    };
    this.isEditMode = true;
  }

  delete(id: number) {
    if (confirm('Delete benefit mapping?')) {
      this.service.delete(id).subscribe(() => this.loadMappings());
    }
  }

  afterSave() {
    this.mapping = this.resetForm();
    this.isEditMode = false;
    this.loadMappings();
  }
}
