import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface TaxConfiguration {
  taxConfigId?: number;
  regimeType: 'OLD' | 'NEW';
  rebateLimit: number;
  exemptionRules: string;
  effectiveFrom: string; // yyyy-MM-dd
  createdAt?: string;
}

@Injectable({ providedIn: 'root' })
export class TaxConfigurationService {

  private baseUrl = `${environment.apiBaseUrl}/tax/config`;

  constructor(private http: HttpClient) {}

  create(config: TaxConfiguration): Observable<TaxConfiguration> {
    return this.http.post<TaxConfiguration>(this.baseUrl, config);
  }

  update(id: number, config: TaxConfiguration): Observable<TaxConfiguration> {
    return this.http.put<TaxConfiguration>(`${this.baseUrl}/${id}`, config);
  }

  getAll(): Observable<TaxConfiguration[]> {
    return this.http.get<TaxConfiguration[]>(this.baseUrl);
  }

  getById(id: number): Observable<TaxConfiguration> {
    return this.http.get<TaxConfiguration>(`${this.baseUrl}/${id}`);
  }
}
