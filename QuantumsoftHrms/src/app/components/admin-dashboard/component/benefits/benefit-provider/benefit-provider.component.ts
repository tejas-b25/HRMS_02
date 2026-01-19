import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BenefitProvider, BenefitProviderService } from '../../../../../services/benefit-provider.service';

@Component({
  selector: 'app-benefit-provider',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './benefit-provider.component.html',
  styleUrls: ['./benefit-provider.component.css']
})
export class BenefitProviderComponent implements OnInit {

  providers: BenefitProvider[] = [];
  provider: BenefitProvider = this.emptyProvider();
  isEditMode = false;

  constructor(private service: BenefitProviderService) {}

  ngOnInit(): void {
    this.loadProviders();
  }

  emptyProvider(): BenefitProvider {
    return {
      providerId: undefined,
      providerName: '',
      isActive: true
    };
  }


  loadProviders() {
    this.service.getAll().subscribe(res => this.providers = res);
  }

  save() {
    if (this.isEditMode && this.provider.providerId) {
      this.service.update(this.provider.providerId, this.provider)
        .subscribe(() => {

          // 🔁 replace updated row in table
          const index = this.providers.findIndex(
            p => p.providerId === this.provider.providerId
          );

          if (index !== -1) {
            this.providers[index] = { ...this.provider };
          }

          this.reset();
        });

    } else {
      this.service.create(this.provider)
        .subscribe((res) => {

          // ➕ push newly created provider with DB ID
          this.providers.push(res);

          this.reset();
        });
    }
  }


  trackByProviderId(index: number, item: BenefitProvider): number | undefined {
    return item.providerId;
  }

  edit(p: BenefitProvider) {
    this.provider = { ...p };
    this.isEditMode = true;
  }

  delete(id?: number) {
    if (!id || !confirm('Delete provider?')) return;

    this.service.delete(id).subscribe(() => {
      this.providers = this.providers.filter(p => p.providerId !== id);
    });
  }

  reset() {
    this.provider = this.emptyProvider();
    this.isEditMode = false;
  }
}