import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompliancesAlertComponent } from './compliances-alert.component';

describe('CompliancesAlertComponent', () => {
  let component: CompliancesAlertComponent;
  let fixture: ComponentFixture<CompliancesAlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompliancesAlertComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CompliancesAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
