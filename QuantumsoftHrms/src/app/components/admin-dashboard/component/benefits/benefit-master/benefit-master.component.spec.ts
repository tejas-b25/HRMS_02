import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BenefitMasterComponent } from './benefit-master.component';

describe('BenefitMasterComponent', () => {
  let component: BenefitMasterComponent;
  let fixture: ComponentFixture<BenefitMasterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BenefitMasterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BenefitMasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
