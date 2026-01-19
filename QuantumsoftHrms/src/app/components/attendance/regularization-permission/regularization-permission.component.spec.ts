import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegularizationPermissionComponent } from './regularization-permission.component';

describe('RegularizationPermissionComponent', () => {
  let component: RegularizationPermissionComponent;
  let fixture: ComponentFixture<RegularizationPermissionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegularizationPermissionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegularizationPermissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
