import { Injectable } from '@angular/core';
import { HttpClient,HttpParams  } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {

  private baseUrl = '/api/announcements';

  constructor(private http: HttpClient) {}

  createAnnouncement(formData: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/create`, formData);
  }

  updateAnnouncement(id: number, formData: FormData): Observable<any> {
    return this.http.put(`${this.baseUrl}/update/${id}`, formData);
  }

  getAllAnnouncements(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/all`);
  }

  getActiveAnnouncements(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/active`);
  }

  softDelete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, { responseType: 'text' });
  }

  hardDelete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/hard-delete/${id}`, { responseType: 'text' });
  }

  // Get active announcements for employees
  getEmployeeAnnouncements(): Observable<any[]> {
    const token = localStorage.getItem('token'); // or sessionStorage

    return this.http.get<any[]>(
      `${this.baseUrl}/employee`,
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );
  }

  markAsRead(announcementId: number): Observable<any> {
    const token = localStorage.getItem('token');

    return this.http.post(
      `${this.baseUrl}/employee/${announcementId}/read`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`
        },
        responseType: 'text'
      }
    );
  }

  markAsAcknowledged(announcementId: number): Observable<any> {
    const token = localStorage.getItem('token');

    return this.http.post(
      `${this.baseUrl}/employee/${announcementId}/ack`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`
        },
        responseType: 'text'
      }
    );
  }


//   getAnnouncementReadStatus(announcementId: number): Observable<any[]> {
//   const token = localStorage.getItem('token');

//   return this.http.get<any[]>(
//     `${this.baseUrl}/${announcementId}/read-status`,
//     {
//       headers: {
//         Authorization: `Bearer ${token}`
//       }
//     }
//   );
// }


}