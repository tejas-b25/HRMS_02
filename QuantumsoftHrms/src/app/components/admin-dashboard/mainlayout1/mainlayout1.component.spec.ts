import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Mainlayout1Component } from './mainlayout1.component';

describe('Mainlayout1Component', () => {
  let component: Mainlayout1Component;
  let fixture: ComponentFixture<Mainlayout1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Mainlayout1Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(Mainlayout1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
