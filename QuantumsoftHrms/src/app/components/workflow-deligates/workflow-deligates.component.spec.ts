import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkflowDeligatesComponent } from './workflow-deligates.component';

describe('WorkflowDeligatesComponent', () => {
  let component: WorkflowDeligatesComponent;
  let fixture: ComponentFixture<WorkflowDeligatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkflowDeligatesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WorkflowDeligatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
