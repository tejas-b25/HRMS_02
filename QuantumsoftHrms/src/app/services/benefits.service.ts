// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';

// export interface Benefit {
//   benefitId?: number;
//   benefitName: string;
//   benefitType: string;
//   description: string;
//   isActive: boolean;
//   createdBy: string;
//   updatedBy: string;
// }

// @Injectable({
//   providedIn: 'root'
// })
// export class BenefitService {

//   private readonly baseUrl = '/api/benefits';

//   constructor(private http: HttpClient) {}

//   // Create benefit
//   createBenefit(benefit: Benefit): Observable<Benefit> {
//     return this.http.post<Benefit>(this.baseUrl, benefit);
//   }

//   // Get all benefits
//   getAllBenefits(): Observable<Benefit[]> {
//     return this.http.get<Benefit[]>(this.baseUrl);
//   }

//   updateBenefit(id: number, benefit: Benefit): Observable<Benefit> {
//     return this.http.put<Benefit>(`${this.baseUrl}/${id}`, benefit);
//   }

//   deleteBenefit(id: number): Observable<any> {
//     return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
//   }


// }

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Benefit {
  benefitId?: number;
  benefitName: string;
  benefitType: string;
  description: string;
  isActive: boolean;
  createdBy: string;
  updatedBy: string;
  createdAt?: string; // optional, returned from backend
  updatedAt?: string; // optional, returned from backend
}

@Injectable({
  providedIn: 'root'
})
export class BenefitService {

  // you can replace with environment.apiBaseUrl if you want
  private readonly baseUrl = '/api/benefits';

  constructor(private http: HttpClient) {}

  // CREATE
  createBenefit(benefit: Benefit): Observable<Benefit> {
    return this.http.post<Benefit>(this.baseUrl, benefit);
  }

  // READ ALL
  getAllBenefits(): Observable<Benefit[]> {
    return this.http.get<Benefit[]>(this.baseUrl);
  }

  // READ ONE
  getBenefitById(id: number): Observable<Benefit> {
    return this.http.get<Benefit>(`${this.baseUrl}/${id}`);
  }

  // UPDATE
  updateBenefit(id: number, benefit: Benefit): Observable<Benefit> {
    return this.http.put<Benefit>(`${this.baseUrl}/${id}`, benefit);
  }

  // DELETE
  deleteBenefit(id: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }
}
