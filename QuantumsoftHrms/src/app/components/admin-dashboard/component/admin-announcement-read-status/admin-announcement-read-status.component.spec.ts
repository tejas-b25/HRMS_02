import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAnnouncementReadStatusComponent } from './admin-announcement-read-status.component';

describe('AdminAnnouncementReadStatusComponent', () => {
  let component: AdminAnnouncementReadStatusComponent;
  let fixture: ComponentFixture<AdminAnnouncementReadStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAnnouncementReadStatusComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminAnnouncementReadStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
