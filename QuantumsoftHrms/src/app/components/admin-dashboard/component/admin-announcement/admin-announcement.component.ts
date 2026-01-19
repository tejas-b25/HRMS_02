import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AnnouncementService } from '../../../../services/announcement.service';
import { TokenUtil } from '../../../../util/tocken.util';

@Component({
  selector: 'app-announcement',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-announcement.component.html',
  styleUrls: ['./admin-announcement.component.css']
})
export class AnnouncementComponent implements OnInit {

  announcements: any[] = [];
  filteredAnnouncements: any[] = [];

  filterType: 'ALL' | 'ACTIVE' | 'DELETED' = 'ALL';
  selectedFile: File | null = null;

  isEditMode = false;
  editAnnouncementId: number | null = null;

  username = '';
  role = '';

  constructor(
    private announcementService: AnnouncementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAnnouncements();
    this.username = TokenUtil.getUsername() || '';
    this.role = TokenUtil.getRole() || '';
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }

  submitAnnouncement(form: NgForm) {
    if (form.invalid) return;

    const formData = new FormData();
    Object.keys(form.value).forEach(key =>
      formData.append(key, form.value[key])
    );

    if (this.selectedFile) {
      formData.append('attachment', this.selectedFile);
    }

    formData.append('createdById', '6');
    formData.append('employeeIds[]', '87');
    formData.append('employeeIds[]', '88');

    if (this.isEditMode && this.editAnnouncementId) {
      this.announcementService
        .updateAnnouncement(this.editAnnouncementId, formData)
        .subscribe(() => this.resetForm(form));
    } else {
      this.announcementService
        .createAnnouncement(formData)
        .subscribe(() => this.resetForm(form));
    }
  }

  editAnnouncement(a: any, form: NgForm) {
    this.isEditMode = true;
    this.editAnnouncementId = a.announcementId;

    form.setValue({
      title: a.title,
      description: a.description,
      category: a.category,
      priority: a.priority,
      targetDepartment: a.targetDepartment,
      targetLocation: a.targetLocation,
      targetRoles: a.targetRoles,
      publishDate: a.publishDate?.substring(0, 16),
      expireDate: a.expireDate?.substring(0, 16),
      email: true,
      push: true,
      inApp: true
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  softDelete(id: number) {
    if (!confirm('Delete announcement?')) return;
    this.announcementService.softDelete(id).subscribe(() => {
      this.loadAnnouncements();
    });
  }

  loadAnnouncements() {
    this.announcementService.getAllAnnouncements().subscribe(data => {
      this.announcements = data;
      this.applyFilter();
    });
  }

  changeFilter(type: 'ALL' | 'ACTIVE' | 'DELETED') {
    this.filterType = type;
    this.applyFilter();
  }

  applyFilter() {
    if (this.filterType === 'ACTIVE') {
      this.filteredAnnouncements = this.announcements.filter(a => a.isActive);
    } else if (this.filterType === 'DELETED') {
      this.filteredAnnouncements = this.announcements.filter(a => !a.isActive);
    } else {
      this.filteredAnnouncements = this.announcements;
    }
  }

  viewReadStatus(id: number) {
    this.router.navigate(['/admin/announcement/read-status', id]);
  }

  resetForm(form: NgForm) {
    form.resetForm();
    this.isEditMode = false;
    this.editAnnouncementId = null;
    this.selectedFile = null;
    this.loadAnnouncements();
  }
}
