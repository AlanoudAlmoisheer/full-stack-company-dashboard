import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerEmployeePieChartComponent } from './employees-by-manager-pie-chart';

describe('ManagerEmployeePieChartComponent', () => {
  let component: ManagerEmployeePieChartComponent;
  let fixture: ComponentFixture<ManagerEmployeePieChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagerEmployeePieChartComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ManagerEmployeePieChartComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
