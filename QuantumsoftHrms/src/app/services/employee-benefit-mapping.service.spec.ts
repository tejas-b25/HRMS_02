import { TestBed } from '@angular/core/testing';

import { EmployeeBenefitMappingService } from './employee-benefit-mapping.service';

describe('EmployeeBenefitMappingService', () => {
  let service: EmployeeBenefitMappingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeBenefitMappingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
