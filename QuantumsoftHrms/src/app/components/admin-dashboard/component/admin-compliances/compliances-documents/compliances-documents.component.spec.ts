import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompliancesDocumentsComponent } from './compliances-documents.component';

describe('CompliancesDocumentsComponent', () => {
  let component: CompliancesDocumentsComponent;
  let fixture: ComponentFixture<CompliancesDocumentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompliancesDocumentsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CompliancesDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
