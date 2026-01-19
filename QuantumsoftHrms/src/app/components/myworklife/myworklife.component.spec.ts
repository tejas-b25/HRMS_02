import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyworklifeComponent } from './myworklife.component';

describe('MyworklifeComponent', () => {
  let component: MyworklifeComponent;
  let fixture: ComponentFixture<MyworklifeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyworklifeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MyworklifeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
