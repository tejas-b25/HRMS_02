import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveCancelComponent } from './leave-cancel.component';

describe('LeaveCancelComponent', () => {
  let component: LeaveCancelComponent;
  let fixture: ComponentFixture<LeaveCancelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeaveCancelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LeaveCancelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
