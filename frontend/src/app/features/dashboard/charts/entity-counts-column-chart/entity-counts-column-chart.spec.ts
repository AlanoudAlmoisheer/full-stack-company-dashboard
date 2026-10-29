import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntityCountsColumnChartComponent } from './entity-counts-column-chart';

describe('EntityCountsColumnChartComponent', () => {
  let component: EntityCountsColumnChartComponent;
  let fixture: ComponentFixture<EntityCountsColumnChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntityCountsColumnChartComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EntityCountsColumnChartComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
