import { TestBed } from '@angular/core/testing';

import { EmployeeTaxService } from './employee-tax.service';

describe('EmployeeTaxService', () => {
  let service: EmployeeTaxService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeTaxService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
