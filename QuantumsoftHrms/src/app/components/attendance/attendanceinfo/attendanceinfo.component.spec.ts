import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendanceinfoComponent } from './attendanceinfo.component';

describe('AttendanceinfoComponent', () => {
  let component: AttendanceinfoComponent;
  let fixture: ComponentFixture<AttendanceinfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AttendanceinfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AttendanceinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
