import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentService, DocumentModel } from '../../../../services/document.service';

@Component({
  selector: 'app-admin-documents',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-document.component.html',
  styleUrls: ['./admin-document.component.css']
})
export class AdminDocumentComponent {

  employeeId!: number;
  documents: DocumentModel[] = [];
  remarks: string = '';
  isLoading = false;
  message: string | null = null;

  selectedFile: File | null = null;
  documentType: string = '';
  isAdmin: any;
  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadDocument() {
    if (!this.selectedFile || !this.employeeId || !this.documentType) {
      alert('Please fill all fields');
      return;
    }

    const uploadedBy = this.isAdmin ? 'Admin' : 'Employee';

    this.documentService.uploadDocument(
      this.selectedFile,
      this.employeeId,
      uploadedBy,
      this.documentType
    ).subscribe({
      next: () => {
        alert('Document uploaded successfully');
        this.loadDocuments(); // refresh list
        this.documentType = '';
        this.selectedFile = null;
      },
      error: () => {
        alert('Upload failed');
      }
    });
  }



  constructor(private documentService: DocumentService) {}

  loadDocuments() {
    if (!this.employeeId) return;

    this.isLoading = true;
    this.documentService.getDocumentsByEmployee(this.employeeId).subscribe({
      next: res => {
        this.documents = res;
        this.isLoading = false;
      },
      error: () => {
        this.message = 'No documents found';
        this.isLoading = false;
      }
    });
  }

  verifyAll() {
    if (!this.remarks) return alert('Remarks required');

    this.documentService.verifyDocuments(this.employeeId, this.remarks).subscribe(res => {
      this.documents = res;
      alert('Documents verified successfully');
    });
  }

  rejectAll() {
    if (!this.remarks) return alert('Remarks required');

    this.documentService.rejectDocuments(this.employeeId, this.remarks).subscribe(res => {
      this.documents = res;
      alert('Documents rejected successfully');
    });
  }

  deleteDocument(id: number) {
    if (!confirm('Delete this document?')) return;

    this.documentService.deleteDocument(id).subscribe(() => {
      this.documents = this.documents.filter(d => d.documentId !== id);
    });
  }

  deleteAll() {
    if (!confirm('Delete ALL documents of this employee?')) return;

    this.documentService.deleteDocumentsByEmployee(this.employeeId).subscribe(() => {
      this.documents = [];
    });
  }

  preview(doc: DocumentModel) {
    window.open(this.documentService.getPreviewUrl(doc.documentId), '_blank');
  }

  download(doc: DocumentModel) {
    window.open(this.documentService.getDownloadUrl(doc.documentId), '_blank');
  }
}