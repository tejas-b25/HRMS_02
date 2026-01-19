import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProofInvestmentComponent } from './proof-investment.component';

describe('ProofInvestmentComponent', () => {
  let component: ProofInvestmentComponent;
  let fixture: ComponentFixture<ProofInvestmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProofInvestmentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProofInvestmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
