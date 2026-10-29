import { TestBed } from '@angular/core/testing';

import { TabState } from './tab-state.ts';

describe('General', () => {
  let service: TabState;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TabState);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
