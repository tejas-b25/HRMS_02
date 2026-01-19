import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PerformanceReviewPayload {
  reviewId?: number;
  employeeId: number;
  kpiId: number;
  reviewPeriod: string;
  selfScore?: number;
  managerScore?: number;
  finalScore?: number;
  feedback?: string;
  reviewStatus?: 'Pending' | 'Completed';
}

@Injectable({ providedIn: 'root' })
export class PerformanceReviewService {

  private baseUrl = '/api/performance';

  constructor(private http: HttpClient) {}

  // CREATE
  createReview(payload: PerformanceReviewPayload): Observable<PerformanceReviewPayload> {
    return this.http.post<PerformanceReviewPayload>(
      `${this.baseUrl}/createReview`, payload
    );
  }

  // UPDATE
  updateReview(id: number, payload: PerformanceReviewPayload): Observable<PerformanceReviewPayload> {
    return this.http.put<PerformanceReviewPayload>(
      `${this.baseUrl}/updateReview/${id}`, payload
    );
  }

  // GET BY ID
  getReviewById(id: number): Observable<PerformanceReviewPayload> {
    return this.http.get<PerformanceReviewPayload>(
      `${this.baseUrl}/getReview/${id}`
    );
  }

  // GET BY EMPLOYEE
  getReviewsByEmployee(employeeId: number): Observable<PerformanceReviewPayload[]> {
    return this.http.get<PerformanceReviewPayload[]>(
      `${this.baseUrl}/employee/${employeeId}`
    );
  }

  // DELETE
  deleteReview(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/Review/${id}`
    );
  }
}
