import { TestBed } from '@angular/core/testing';

import { RegularizationRequestService } from './regularization-request.service';

describe('RegularizationRequestService', () => {
  let service: RegularizationRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegularizationRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
