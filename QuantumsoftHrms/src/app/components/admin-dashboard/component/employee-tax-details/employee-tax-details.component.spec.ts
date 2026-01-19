import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeTaxDetailsComponent } from './employee-tax-details.component';

describe('EmployeeTaxDetailsComponent', () => {
  let component: EmployeeTaxDetailsComponent;
  let fixture: ComponentFixture<EmployeeTaxDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeTaxDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeTaxDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
