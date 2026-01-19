// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';

// export interface PayrollStructure {
//   structureId?: number;
//   structureName: string;
//   basicPay: number;
//   hra: number;
//   variablePay: number;
//   otherAllowances: number;
//   deductions: number;

//   // optional fields from backend
//   grossSalary?: number;
//   netSalary?: number;
//   createdAt?: string;
//   updatedAt?: string;
// }

// @Injectable({
//   providedIn: 'root'
// })
// export class PayrollStructureService {

//   private readonly baseUrl = '/api/payroll/structure';

//   constructor(private http: HttpClient) {}

//   // CREATE
//   createStructure(payload: PayrollStructure): Observable<PayrollStructure> {
//     return this.http.post<PayrollStructure>(this.baseUrl, payload);
//   }

//   // READ ALL (if backend supports GET on same URL)
//   getAllStructures(): Observable<PayrollStructure[]> {
//     return this.http.get<PayrollStructure[]>(this.baseUrl);
//   }

//   // UPDATE (if backend supports PUT /structure/{id})
//   updateStructure(id: number, payload: PayrollStructure): Observable<PayrollStructure> {
//     return this.http.put<PayrollStructure>(`${this.baseUrl}/${id}`, payload);
//   }

//   // DELETE (if backend supports DELETE /structure/{id})
//   deleteStructure(id: number): Observable<string> {
//     return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
//   }
// }
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PayrollStructure {
  structureId?: number;      // optional, for update/edit
  structureName: string;
  basicPay: number;
  hra: number;
  variablePay: number;
  otherAllowances: number;
  deductions: number;
}

@Injectable({
  providedIn: 'root'
})
export class PayrollStructureService {

  private readonly baseUrl = '/api/payroll/structure';

  constructor(private http: HttpClient) {}

  /**
   * CREATE or UPDATE structure.
   * Backend decides based on presence of structureId (if it uses that).
   * Body shape matches your sample:
   * {
   *   "structureName": "Admin Structure",
   *   "basicPay": 16000,
   *   "hra": 7000,
   *   "variablePay": 4500,
   *   "otherAllowances": 2000,
   *   "deductions": 2500
   * }
   */
  saveStructure(payload: PayrollStructure): Observable<PayrollStructure> {
    return this.http.post<PayrollStructure>(this.baseUrl, payload);
  }

  /**
   * GET BY ID: /api/payroll/structure/{id}
   * Only used when user explicitly clicks "Load by ID"
   */
  getStructureById(id: number): Observable<PayrollStructure> {
    return this.http.get<PayrollStructure>(`${this.baseUrl}/${id}`);
  }
}
