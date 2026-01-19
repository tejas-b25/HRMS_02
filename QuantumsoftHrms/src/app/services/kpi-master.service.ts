import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface KpiPayload {
  kpiId?: number;
  departmentId: number;
  role: 'EMPLOYEE' | 'MANAGER' | 'HR' | 'ADMIN';
  kpiName: string;
  kpiDescription: string;
  weightage: number;
}

@Injectable({ providedIn: 'root' })
export class KpiMasterService {

  private baseUrl = '/api/performance';

  constructor(private http: HttpClient) {}

  createKpi(payload: KpiPayload): Observable<KpiPayload> {
    return this.http.post<KpiPayload>(`${this.baseUrl}/create`, payload);
  }

  updateKpi(id: number, payload: KpiPayload): Observable<KpiPayload> {
    return this.http.put<KpiPayload>(`${this.baseUrl}/update/${id}`, payload);
  }

  getAllKpis(): Observable<KpiPayload[]> {
    return this.http.get<KpiPayload[]>(`${this.baseUrl}/allkpi`);
  }

  getKpiById(id: number): Observable<KpiPayload> {
    return this.http.get<KpiPayload>(`${this.baseUrl}/kpi/${id}`);
  }

  deleteKpi(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/kpi/${id}`);
  }
}
