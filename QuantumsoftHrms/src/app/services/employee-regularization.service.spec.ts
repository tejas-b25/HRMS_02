import { TestBed } from '@angular/core/testing';

import { EmployeeRegularizationService } from './employee-regularization.service';

describe('EmployeeRegularizationService', () => {
  let service: EmployeeRegularizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeRegularizationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
