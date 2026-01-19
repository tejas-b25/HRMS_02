import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KudosComponent } from './kudos.component';

describe('KudosComponent', () => {
  let component: KudosComponent;
  let fixture: ComponentFixture<KudosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KudosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(KudosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
