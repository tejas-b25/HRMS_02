import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PerformanceDashboard {
  dashboardId?: number;
  departmentName: string;
  avgScore: number;
  totalEmployees: number;
  topScore: number;
  bottomScore: number;
  reviewPeriod: string;
  generatedAt?: string;
}

@Injectable({ providedIn: 'root' })
export class PerformanceDashboardService {

  private baseUrl = '/api/performance';

  constructor(private http: HttpClient) {}

  createDashboard(payload: PerformanceDashboard): Observable<PerformanceDashboard> {
    return this.http.post<PerformanceDashboard>(
      `${this.baseUrl}/dashboard`, payload
    );
  }

  getAllDashboards(): Observable<PerformanceDashboard[]> {
    return this.http.get<PerformanceDashboard[]>(
      `${this.baseUrl}/gellAllDashboard`
    );
  }

  getByDepartment(department: string): Observable<PerformanceDashboard[]> {
    return this.http.get<PerformanceDashboard[]>(
      `${this.baseUrl}/department/${department}`
    );
  }

  getByPeriod(period: string): Observable<PerformanceDashboard[]> {
    return this.http.get<PerformanceDashboard[]>(
      `${this.baseUrl}/period/${period}`
    );
  }

  deleteDashboard(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/dashboard/${id}`
    );
  }
}
