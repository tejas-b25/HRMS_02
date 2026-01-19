import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BenefitProviderComponent } from './benefit-provider.component';

describe('BenefitProviderComponent', () => {
  let component: BenefitProviderComponent;
  let fixture: ComponentFixture<BenefitProviderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BenefitProviderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BenefitProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
