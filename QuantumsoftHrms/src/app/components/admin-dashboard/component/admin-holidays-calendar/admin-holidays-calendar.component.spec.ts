import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminHolidaysCalendarComponent } from './admin-holidays-calendar.component';

describe('AdminHolidaysCalendarComponent', () => {
  let component: AdminHolidaysCalendarComponent;
  let fixture: ComponentFixture<AdminHolidaysCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminHolidaysCalendarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminHolidaysCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
