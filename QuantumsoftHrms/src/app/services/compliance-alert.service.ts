import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ComplianceAlertService {

  private baseUrl = '/api/compliance/alerts';

  constructor(private http: HttpClient) {}

  getAllAlerts(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  getUpcomingAlerts(days: number): Observable<any[]> {
    const params = new HttpParams().set('days', days);
    return this.http.get<any[]>(`${this.baseUrl}/upcoming`, { params });
  }

  getAlertHistory(filters: any): Observable<any[]> {
    let params = new HttpParams();
    if (filters.status) params = params.set('status', filters.status);
    if (filters.channel) params = params.set('channel', filters.channel);
    if (filters.fromDate) params = params.set('fromDate', filters.fromDate);
    if (filters.toDate) params = params.set('toDate', filters.toDate);

    return this.http.get<any[]>(`${this.baseUrl}/history`, { params });
  }

  createAlert(payload: any): Observable<any> {
    return this.http.post(this.baseUrl, payload);
  }

  updateAlert(alertId: number, payload: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${alertId}`, payload);
  }

  deleteAlert(alertId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${alertId}`, { responseType: 'text' });
  }
}
