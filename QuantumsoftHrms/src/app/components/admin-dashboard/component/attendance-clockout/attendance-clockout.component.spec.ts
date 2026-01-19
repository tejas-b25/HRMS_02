import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendanceClockoutComponent } from './attendance-clockout.component';

describe('AttendanceClockoutComponent', () => {
  let component: AttendanceClockoutComponent;
  let fixture: ComponentFixture<AttendanceClockoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AttendanceClockoutComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AttendanceClockoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
