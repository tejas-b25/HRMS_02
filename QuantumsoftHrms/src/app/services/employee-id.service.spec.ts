import { TestBed } from '@angular/core/testing';

import { EmployeeIDService } from './employee-id.service';

describe('EmployeeIDService', () => {
  let service: EmployeeIDService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeIDService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
