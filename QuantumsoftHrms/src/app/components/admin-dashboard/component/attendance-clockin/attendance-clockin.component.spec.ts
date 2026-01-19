import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendanceClockinComponent } from './attendance-clockin.component';

describe('AttendanceClockinComponent', () => {
  let component: AttendanceClockinComponent;
  let fixture: ComponentFixture<AttendanceClockinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AttendanceClockinComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AttendanceClockinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
