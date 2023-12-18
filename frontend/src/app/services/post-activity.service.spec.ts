import { TestBed } from '@angular/core/testing';

import { PostActivityService } from './post-activity.service';

describe('PostActivityService', () => {
  let service: PostActivityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostActivityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
