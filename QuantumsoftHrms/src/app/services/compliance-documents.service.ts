import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComplianceDocumentsService {

  private baseUrl = '/api/compliance/documents';

  constructor(private http: HttpClient) {}

  // Upload document
  uploadDocument(formData: FormData): Observable<any> {
    return this.http.post(this.baseUrl, formData);
  }

  // Download by documentId
  downloadDocument(documentId: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/get/${documentId}`, {
      responseType: 'blob'
    });
  }

  // Download employee latest document
  downloadEmployeeDocument(employeeId: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/get/employee/${employeeId}`, {
      responseType: 'blob'
    });
  }

  // Verify document
  verifyDocument(documentId: number, verifiedBy: string): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/verify/${documentId}?verifiedBy=${verifiedBy}`,
      {}
    );
  }

  // Get expired documents
  getExpiredDocuments(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/expired`);
  }

  // Delete document
  deleteDocument(documentId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${documentId}`, {
      responseType: 'text'
    });
  }
}