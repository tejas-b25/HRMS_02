import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EmployeeBenefitMapping {
  mappingId?: number;
  employeeId: number;
  benefit: { benefitId: number };
  provider: { providerId: number };
  coverageAmount: number;
  premiumAmount: number;
  employerContribution: number;
  startDate: string;
  endDate: string;
  status: string;
}

@Injectable({ providedIn: 'root' })
export class EmployeeBenefitMappingService {

  private baseUrl = '/api/employee-benefits';

  constructor(private http: HttpClient) {}

  getAll(): Observable<EmployeeBenefitMapping[]> {
    return this.http.get<EmployeeBenefitMapping[]>(this.baseUrl);
  }

  getById(id: number): Observable<EmployeeBenefitMapping> {
    return this.http.get<EmployeeBenefitMapping>(`${this.baseUrl}/${id}`);
  }

  create(data: EmployeeBenefitMapping): Observable<EmployeeBenefitMapping> {
    return this.http.post<EmployeeBenefitMapping>(this.baseUrl, data);
  }

  update(id: number, data: EmployeeBenefitMapping): Observable<EmployeeBenefitMapping> {
    return this.http.put<EmployeeBenefitMapping>(`${this.baseUrl}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}