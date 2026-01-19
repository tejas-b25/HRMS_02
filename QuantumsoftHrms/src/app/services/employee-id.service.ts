import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = '/api/employees';

  constructor(private http: HttpClient) {}

  searchEmployeeByName(name: string): Observable<any> {
    const params = new HttpParams().set('name', name);
    return this.http.get(`${this.baseUrl}/search`, { params });
  }
}