import { TestBed } from '@angular/core/testing';

import { ComplianceDocumentsService } from './compliance-documents.service';

describe('ComplianceDocumentsService', () => {
  let service: ComplianceDocumentsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComplianceDocumentsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
