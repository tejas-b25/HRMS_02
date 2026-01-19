import { TestBed } from '@angular/core/testing';

import { FeedbackThreeSixtyService } from './feedback-three-sixty.service';

describe('FeedbackThreeSixtyService', () => {
  let service: FeedbackThreeSixtyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FeedbackThreeSixtyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
