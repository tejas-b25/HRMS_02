import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeemasterReportComponent } from './employeemaster-report.component';

describe('EmployeemasterReportComponent', () => {
  let component: EmployeemasterReportComponent;
  let fixture: ComponentFixture<EmployeemasterReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeemasterReportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeemasterReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
