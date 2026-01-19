// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';

// export interface BenefitProvider {
//   providerId?: number;
//   providerName: string;
//   contactPerson?: string;
//   contactEmail?: string;
//   contactPhone?: string;
//   policyNumber?: string;
//   renewalDate?: string;
//   address?: string;
//   isActive: boolean;
// }

// @Injectable({ providedIn: 'root' })
// export class BenefitProviderService {

//   private baseUrl = '/api/providers';

//   constructor(private http: HttpClient) {}

//   getAll(): Observable<BenefitProvider[]> {
//     return this.http.get<BenefitProvider[]>(this.baseUrl);
//   }

//   getById(id: number): Observable<BenefitProvider> {
//     return this.http.get<BenefitProvider>(`${this.baseUrl}/${id}`);
//   }

//   create(data: BenefitProvider): Observable<BenefitProvider> {
//     return this.http.post<BenefitProvider>(this.baseUrl, data);
//   }

//   update(id: number, data: BenefitProvider): Observable<any> {
//     return this.http.put(`${this.baseUrl}/${id}`, data);
//   }

//   delete(id: number): Observable<any> {
//     return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
//   }
// }

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BenefitProvider {
  providerId?: number;
  providerName: string;
  contactPerson?: string;
  contactEmail?: string;
  contactPhone?: string;
  policyNumber?: string;
  renewalDate?: string;
  address?: string;
  isActive: boolean;
}

@Injectable({ providedIn: 'root' })
export class BenefitProviderService {

  private baseUrl = '/api/providers';

  constructor(private http: HttpClient) {}

  getAll(): Observable<BenefitProvider[]> {
    return this.http.get<BenefitProvider[]>(this.baseUrl);
  }

  getById(id: number): Observable<BenefitProvider> {
    return this.http.get<BenefitProvider>(`${this.baseUrl}/${id}`);
  }

  create(data: BenefitProvider): Observable<BenefitProvider> {
    return this.http.post<BenefitProvider>(this.baseUrl, data);
  }

  update(id: number, data: BenefitProvider): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, data);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }
}