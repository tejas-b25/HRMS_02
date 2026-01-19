import { TestBed } from '@angular/core/testing';

import { BenefitProviderService } from './benefit-provider.service';

describe('BenefitProviderService', () => {
  let service: BenefitProviderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BenefitProviderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
