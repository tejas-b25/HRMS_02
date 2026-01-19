import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewmyinfoComponent } from './viewmyinfo.component';

describe('ViewmyinfoComponent', () => {
  let component: ViewmyinfoComponent;
  let fixture: ComponentFixture<ViewmyinfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewmyinfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewmyinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
