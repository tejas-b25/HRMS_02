import { TestBed } from '@angular/core/testing';

import { EmployeeBankService } from './employee-bank.service';

describe('EmployeeBankService', () => {
  let service: EmployeeBankService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeBankService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
