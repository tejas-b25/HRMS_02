import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItDeclarationComponent } from './it-declaration.component';

describe('ItDeclarationComponent', () => {
  let component: ItDeclarationComponent;
  let fixture: ComponentFixture<ItDeclarationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItDeclarationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ItDeclarationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
