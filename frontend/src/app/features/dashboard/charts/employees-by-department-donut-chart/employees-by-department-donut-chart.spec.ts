import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeesByDepartmentDonutChartComponent } from './employees-by-department-donut-chart';

describe('EmployeesByDepartmentDonutChartComponent', () => {
  let component: EmployeesByDepartmentDonutChartComponent;
  let fixture: ComponentFixture<EmployeesByDepartmentDonutChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeesByDepartmentDonutChartComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EmployeesByDepartmentDonutChartComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
