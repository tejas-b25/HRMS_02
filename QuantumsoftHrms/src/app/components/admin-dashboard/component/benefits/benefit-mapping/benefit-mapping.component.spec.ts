import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BenefitMappingComponent } from './benefit-mapping.component';

describe('BenefitMappingComponent', () => {
  let component: BenefitMappingComponent;
  let fixture: ComponentFixture<BenefitMappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BenefitMappingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BenefitMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
