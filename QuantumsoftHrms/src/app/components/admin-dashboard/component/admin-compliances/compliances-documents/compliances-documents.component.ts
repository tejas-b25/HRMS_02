import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComplianceDocumentsService } from '../../../../../services/compliance-documents.service';

@Component({
  selector: 'app-compliance-documents',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './compliances-documents.component.html',
  styleUrls: ['./compliances-documents.component.css']
})
export class CompliancesDocumentsComponent {

  documents: any[] = [];
  selectedFile: File | null = null;

  fileInvalid = false;
  submitted = false;

  form = {
    complianceId: '',
    complianceMappingId: '',
    employeeId: '',
    expiryDate: '',
    uploadedBy: 'HR'
  };

  isLoading = false;
  message = '';

  constructor(private service: ComplianceDocumentsService) {}

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
    this.fileInvalid = !this.selectedFile;
  }

  isInvalid(value: any) {
    return this.submitted && (!value || value.toString().trim() === '');
  }

  upload() {
    this.submitted = true;
    this.fileInvalid = !this.selectedFile;

    if (
      this.isInvalid(this.form.complianceId) ||
      this.isInvalid(this.form.complianceMappingId) ||
      this.isInvalid(this.form.employeeId) ||
      this.isInvalid(this.form.expiryDate) ||
      this.fileInvalid
    ) {
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile!);
    formData.append('uploadedBy', this.form.uploadedBy);
    formData.append('expiryDate', this.form.expiryDate);
    formData.append('complianceId', this.form.complianceId);
    formData.append('employeeId', this.form.employeeId);
    formData.append('complianceMappingId', this.form.complianceMappingId);

    this.service.uploadDocument(formData).subscribe({
      next: (res) => {
        this.message = 'Document uploaded successfully';
        this.documents.unshift(res);
        this.reset();
      },
      error: () => alert('Upload failed')
    });
  }

  reset() {
    this.form = {
      complianceId: '',
      complianceMappingId: '',
      employeeId: '',
      expiryDate: '',
      uploadedBy: 'HR'
    };
    this.selectedFile = null;
    this.submitted = false;
    this.fileInvalid = false;
  }

  download(doc: any) {
    this.service.downloadDocument(doc.documentId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = doc.fileName;
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  verify(doc: any) {
    this.service.verifyDocument(doc.documentId, 'HR').subscribe(res => {
      doc.status = res.status;
      doc.isVerified = true;
    });
  }

  delete(doc: any) {
    if (!confirm('Delete document?')) return;

    this.service.deleteDocument(doc.documentId).subscribe(() => {
      this.documents = this.documents.filter(d => d.documentId !== doc.documentId);
    });
  }

  loadExpired() {
    this.isLoading = true;
    this.service.getExpiredDocuments().subscribe({
      next: res => {
        this.documents = res;
        this.isLoading = false;
      },
      error: () => {
        this.documents = [];
        this.isLoading = false;
      }
    });
  }
}
