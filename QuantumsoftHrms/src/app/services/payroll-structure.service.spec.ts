import { TestBed } from '@angular/core/testing';

import { PayrollStructureService } from './payroll-structure.service';

describe('PayrollStructureService', () => {
  let service: PayrollStructureService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PayrollStructureService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
