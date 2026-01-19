import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface FeedbackPayload {
  reviewerId: number;
  reviewerName: string;
  revieweeId: number;
  revieweeName: string;
  comments: string;
  rating: number;
}

export interface FeedbackResponse extends FeedbackPayload {
  feedbackId: number;
  createdAt: string;
  isDeleted: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class FeedbackThreeSixtyService {

  private baseUrl = '/api/performance';

  constructor(private http: HttpClient) {}

  // ✅ CREATE
  createFeedback(payload: FeedbackPayload): Observable<FeedbackResponse> {
    return this.http.post<FeedbackResponse>(
      `${this.baseUrl}/feedback/create`,
      payload
    );
  }

  // ✅ GET ALL (ADMIN / DASHBOARD)
 getAllFeedback(): Observable<FeedbackResponse[]> {
  return this.http.get<FeedbackResponse[]>(
    `${this.baseUrl}/getAllFeedback`
  );
}


  // ✅ RECEIVED FEEDBACK (EMPLOYEE)
  getFeedbackByReviewee(revieweeId: number): Observable<FeedbackResponse[]> {
    return this.http.get<FeedbackResponse[]>(
      `${this.baseUrl}/Feedbackreviewee/${revieweeId}`
    );
  }

  // ✅ DELETE
  deleteFeedback(id: number): Observable<string> {
    return this.http.delete<string>(
      `${this.baseUrl}/${id}`
    );
  }
  
}
