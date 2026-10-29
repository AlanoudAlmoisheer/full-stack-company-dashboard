import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeJobTitleChartComponent } from './employees-by-job-title-column-chart';

describe('EmployeeJobTitleChart', () => {
  let component: EmployeeJobTitleChartComponent;
  let fixture: ComponentFixture<EmployeeJobTitleChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeJobTitleChartComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeJobTitleChartComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
