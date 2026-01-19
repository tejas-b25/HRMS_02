import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // ✅ REQUIRED
import { DocumentService, DocumentModel } from '../../services/document.service';
import { EmployeeUtil } from '../../util/employee-id.util';

@Component({
  selector: 'app-document-center',
  standalone: true,
  imports: [CommonModule, FormsModule], // ✅ MUST include FormsModule
  templateUrl: './document-center.component.html',
  styleUrls: ['./document-center.component.css'],
})
export class DocumentCenterComponent implements OnInit {

  employeeId!: number;
  documents: DocumentModel[] = [];

  selectedFile: File | null = null;
  documentType: string = '';

  isLoading = false;
  isAdmin: any;

  constructor(private documentService: DocumentService) {}

  ngOnInit(): void {
    this.employeeId = EmployeeUtil.getEmployeeId();
    this.loadDocuments();
  }

  loadDocuments() {
    this.isLoading = true;
    this.documentService.getDocumentsByEmployee(this.employeeId).subscribe({
      next: res => {
        this.documents = res;
        this.isLoading = false;
      },
      error: () => this.isLoading = false
    });
  }

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


  preview(doc: DocumentModel) {
    window.open(this.documentService.getPreviewUrl(doc.documentId), '_blank');
  }

  download(doc: DocumentModel) {
    window.open(this.documentService.getDownloadUrl(doc.documentId), '_blank');
  }
}