import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequesthubComponent } from './requesthub.component';

describe('RequesthubComponent', () => {
  let component: RequesthubComponent;
  let fixture: ComponentFixture<RequesthubComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RequesthubComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RequesthubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
