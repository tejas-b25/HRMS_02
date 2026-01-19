import { TestBed } from '@angular/core/testing';

import { ComplianceAlertService } from './compliance-alert.service';

describe('ComplianceAlertService', () => {
  let service: ComplianceAlertService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComplianceAlertService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
