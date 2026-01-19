import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Holiday {
  holidayId: number;
  holidayName: string;
  holidayDate: string;
  region: string;
  optional: boolean;
  holidayType: string;
  description: string;
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdminHolidaysService {

  private baseUrl = '/api/holidays';

  constructor(private http: HttpClient) {}

  getCalendar(year: number, region?: string): Observable<Holiday[]> {
    let params = new HttpParams().set('year', year);
    if (region) params = params.set('region', region);
    return this.http.get<Holiday[]>(`${this.baseUrl}/calendar`, { params });
  }

  createHoliday(payload: any): Observable<Holiday> {
    return this.http.post<Holiday>(this.baseUrl, payload);
  }

  updateHoliday(id: number, payload: any): Observable<Holiday> {
    return this.http.put<Holiday>(`${this.baseUrl}/${id}`, payload);
  }

  deleteHoliday(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}