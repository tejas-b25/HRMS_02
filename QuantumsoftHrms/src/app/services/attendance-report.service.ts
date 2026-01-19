import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export type ExportType = 'csv' | 'excel' | 'pdf';

@Injectable({ providedIn: 'root' })
export class AttendanceReportService {

  private baseUrl = '/api/reports';

  constructor(private http: HttpClient) {}

  downloadAttendanceReport(
    exportType: ExportType,
    fromDate: string,
    toDate: string
  ): Observable<string> {

    const params = new HttpParams()
      .set('reportType', 'Attendance')
      .set('exportType', exportType)
      .set('fromDate', fromDate)
      .set('toDate', toDate);

    return this.http.post(
      `${this.baseUrl}/export`,
      null,
      { params, responseType: 'text' }
    );
  }


  
}
