import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EmployeeBankDetails {
  id?: number;                // optional DB id if backend returns it
  employeeId: number;
  bankName: string;
  accountHolderName: string;
  accountNumber: string;
  accountType: string;        // e.g. "Savings"
  ifsc: string;
  branch: string;
  uanNumber?: string;
  createdAt?: string;
  updatedAt?: string;
}

@Injectable({
  providedIn: 'root'
})
export class EmployeeBankService {
  private readonly baseUrl = '/api/employeeBankDetails';

  constructor(private http: HttpClient) {}

  // CREATE or SAVE bank details (POST)
  saveBankDetails(payload: EmployeeBankDetails): Observable<EmployeeBankDetails> {
    return this.http.post<EmployeeBankDetails>(this.baseUrl, payload);
  }

  // GET by employeeId (assumes GET /api/employeeBankDetails/{employeeId})
  getByEmployeeId(employeeId: number): Observable<EmployeeBankDetails> {
    return this.http.get<EmployeeBankDetails>(`${this.baseUrl}/${employeeId}`);
  }

  // UPDATE (PUT /api/employeeBankDetails/{id} if supported)
  updateBankDetails(id: number, payload: EmployeeBankDetails): Observable<EmployeeBankDetails> {
    return this.http.put<EmployeeBankDetails>(`${this.baseUrl}/${id}`, payload);
  }

  // DELETE (DELETE /api/employeeBankDetails/{id})
  deleteBankDetails(id: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }
}
