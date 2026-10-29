import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerFormComponent } from './manager-form';

describe('ManagerForm', () => {
  let component: ManagerFormComponent;
  let fixture: ComponentFixture<ManagerFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagerFormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ManagerFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
