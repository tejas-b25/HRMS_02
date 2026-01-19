import { TestBed } from '@angular/core/testing';

import { PayrollProcessService } from './payroll-process.service';

describe('PayrollProcessService', () => {
  let service: PayrollProcessService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PayrollProcessService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
