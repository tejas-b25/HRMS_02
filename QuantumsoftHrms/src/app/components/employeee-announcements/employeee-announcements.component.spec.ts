import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeeAnnouncementsComponent } from './employeee-announcements.component';

describe('EmployeeeAnnouncementsComponent', () => {
  let component: EmployeeeAnnouncementsComponent;
  let fixture: ComponentFixture<EmployeeeAnnouncementsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeeAnnouncementsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeeAnnouncementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
