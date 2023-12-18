import { TestBed } from '@angular/core/testing';

import { SharedImageUserService } from './shared-image-user.service';

describe('SharedImageUserService', () => {
  let service: SharedImageUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedImageUserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
