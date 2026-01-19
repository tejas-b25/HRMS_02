import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompliancesMappingComponent } from './compliances-mapping.component';

describe('CompliancesMappingComponent', () => {
  let component: CompliancesMappingComponent;
  let fixture: ComponentFixture<CompliancesMappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompliancesMappingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CompliancesMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
