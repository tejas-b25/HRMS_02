import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DocumentModel {
  documentId: number;
  documentType: string;
  fileName: string;
  filePath: string;
  uploadedBy: string;
  uploadedAt: string;
  status: string;
  remarks: string;
  storageType: string;
  verifiedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  private baseUrl = '/api/documents';

  constructor(private http: HttpClient) {}

  uploadDocument(
    file: File,
    employeeId: number,
    uploadedBy: string,
    documentType: string
  ) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('employeeId', employeeId.toString());
    formData.append('uploadedBy', uploadedBy);
    formData.append('documentType', documentType);

    return this.http.post(`${this.baseUrl}/upload`, formData);
  }


  // Get documents by employee
  getDocumentsByEmployee(employeeId: number): Observable<DocumentModel[]> {
    return this.http.get<DocumentModel[]>(`${this.baseUrl}/employee/${employeeId}`);
  }

  // Verify all documents of employee
  verifyDocuments(employeeId: number, remarks: string): Observable<DocumentModel[]> {
    const params = new HttpParams().set('remarks', remarks);
    return this.http.put<DocumentModel[]>(`${this.baseUrl}/${employeeId}/verify`, null, { params });
  }

  // Reject all documents of employee
  rejectDocuments(employeeId: number, remarks: string): Observable<DocumentModel[]> {
    const params = new HttpParams().set('remarks', remarks);
    return this.http.put<DocumentModel[]>(`${this.baseUrl}/${employeeId}/reject`, null, { params });
  }

  // Delete single document
  deleteDocument(documentId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${documentId}`);
  }

  // Delete all documents by employee
  deleteDocumentsByEmployee(employeeId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/employee/${employeeId}`);
  }

  // Preview URL
  getPreviewUrl(documentId: number): string {
    return `${this.baseUrl}/${documentId}/preview`;
  }

  // Download URL
  getDownloadUrl(documentId: number): string {
    return `${this.baseUrl}/${documentId}/download`;
  }
}