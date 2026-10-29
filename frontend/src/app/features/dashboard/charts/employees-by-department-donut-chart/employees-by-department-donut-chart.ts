import { Component, OnInit, CUSTOM_ELEMENTS_SCHEMA, ChangeDetectorRef } from '@angular/core';
import * as Highcharts from 'highcharts';
import { HighchartsChartComponent } from 'highcharts-angular';
import { DepartmentEmployeeDonutService } from '../../services/employees-by-department-chart';
import { CommonModule } from '@angular/common';
import { CHART_PALETTE } from '../chart-palette';

@Component({
  selector: 'app-employees-by-department-donut-chart',
  standalone: true,
  imports: [HighchartsChartComponent, CommonModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './employees-by-department-donut-chart.html',
  styleUrls: ['./employees-by-department-donut-chart.scss'],
})
export class EmployeesByDepartmentDonutChartComponent implements OnInit {

  Highcharts: typeof Highcharts = Highcharts;

  chartOptions: Highcharts.Options = {};

  chartSize = { width: 400, height: 300 };

  constructor(
    private departmentEmployeeDonutService: DepartmentEmployeeDonutService,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadChart();
  }

  loadChart(): void {
    this.departmentEmployeeDonutService.getData().subscribe(data => {

      const seriesData = data.map((item, index) => ({
        name: item.departmentName,
        y: item.employeeCount,
        color: CHART_PALETTE.sequence[index % CHART_PALETTE.sequence.length]
      }));

      this.chartOptions = {
        chart: {
          type: 'pie',
          height: 280,
          backgroundColor: 'transparent'
        },

        title: {
          text: 'Employees per Department',
          style: {
            color: '#1E293B',
            fontSize: '18px',
            fontWeight: '600'
          }
        },

        tooltip: {
          pointFormat: '<b>{point.y}</b>'
        },

        plotOptions: {
          pie: {
            innerSize: '60%',
            dataLabels: {
              enabled: true,
              format: '{point.name}: {point.y}',

              style: {
                color: '#1E293B',
                fontSize: '13px',
                fontWeight: '400',
                textAlign: 'center'
              }
            },
            borderWidth: 0
          }
        },

        series: [{
          name: 'Employees',
          type: 'pie',
          data: seriesData
        }]
      };

      this.cd.detectChanges();
    });
  }
}