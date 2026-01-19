import { TestBed } from '@angular/core/testing';

import { KpiMasterService } from './kpi-master.service';

describe('KpiMasterService', () => {
  let service: KpiMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KpiMasterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
