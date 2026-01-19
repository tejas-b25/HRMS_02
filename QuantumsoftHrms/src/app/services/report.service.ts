import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export type ExportType = 'csv' | 'excel' | 'pdf';
export type ReportType =
  | 'EmployeeMaster'
  | 'Attendance'
  | 'ComplianceSummary'
  | 'Leave';

@Injectable({ providedIn: 'root' })
export class ReportService {

  private baseUrl = '/api/reports';

  constructor(private http: HttpClient) {}

  exportReport(
    reportType: ReportType,
    exportType: ExportType,
    fromDate?: string,
    toDate?: string
  ): Observable<string> {

    let params = new HttpParams()
      .set('reportType', reportType)
      .set('exportType', exportType);

    if (fromDate) params = params.set('fromDate', fromDate);
    if (toDate) params = params.set('toDate', toDate);

    return this.http.post(
      `${this.baseUrl}/export`,
      null,
      { params, responseType: 'text' }
    );
  }
}
