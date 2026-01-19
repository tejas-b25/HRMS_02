import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveCompOffComponent } from './leave-comp-off.component';

describe('LeaveCompOffComponent', () => {
  let component: LeaveCompOffComponent;
  let fixture: ComponentFixture<LeaveCompOffComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeaveCompOffComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LeaveCompOffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
