import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { TaxConfiguration, TaxConfigurationService } from '../../../../services/tax-configuration.service';

@Component({
  selector: 'app-tax-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tax-management.component.html',
  styleUrls: ['./tax-management.component.css']
})
export class TaxManagementComponent implements OnInit {

  configs: TaxConfiguration[] = [];
  isEdit = false;

  formData: TaxConfiguration = {
    regimeType: 'OLD',
    rebateLimit: 0,
    exemptionRules: '',
    effectiveFrom: ''
  };

  loading = false;
  error = '';
  success = '';

  constructor(private taxService: TaxConfigurationService) {}

  ngOnInit(): void {
    this.loadConfigs();
  }

  loadConfigs() {
    this.loading = true;
    this.taxService.getAll().subscribe({
      next: data => {
        this.configs = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load tax configurations';
        this.loading = false;
      }
    });
  }

  edit(config: TaxConfiguration) {
    this.isEdit = true;
    this.formData = { ...config };
    this.success = '';
    this.error = '';
  }

  reset(form: NgForm) {
    this.isEdit = false;
    form.resetForm({
      regimeType: 'OLD'
    });
  }

  submit(form: NgForm) {
    if (form.invalid) return;

    this.loading = true;
    this.error = '';
    this.success = '';

    const action = this.isEdit && this.formData.taxConfigId
      ? this.taxService.update(this.formData.taxConfigId, this.formData)
      : this.taxService.create(this.formData);

    action.subscribe({
      next: () => {
        this.success = this.isEdit
          ? 'Tax configuration updated successfully'
          : 'Tax configuration created successfully';
        this.reset(form);
        this.loadConfigs();
        this.loading = false;
      },
      error: err => {
        this.error = err?.error?.message || 'Operation failed';
        this.loading = false;
      }
    });
  }
}
