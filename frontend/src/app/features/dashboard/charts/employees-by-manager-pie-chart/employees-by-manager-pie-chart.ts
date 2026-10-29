import { Component, OnInit, CUSTOM_ELEMENTS_SCHEMA, ChangeDetectorRef } from '@angular/core';
import * as Highcharts from 'highcharts';
import { HighchartsChartComponent } from 'highcharts-angular';
import { ManagerEmployeePieChartService } from '../../services/employees-by-manager-chart';
import { CHART_PALETTE } from '../chart-palette';


@Component({
  selector: 'app-employees-by-manager-pie-chart',
  standalone: true,
  imports: [HighchartsChartComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './employees-by-manager-pie-chart.html',
  styleUrls: ['./employees-by-manager-pie-chart.scss'],
})
export class ManagerEmployeePieChartComponent implements OnInit {
  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {};
  chartSize = { width: 400, height: 300 };



  constructor(private managerEmployeePieChartService: ManagerEmployeePieChartService
    , private cd: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadChart();
  }

  loadChart(): void {

    this.managerEmployeePieChartService.getData().subscribe(data => {

      const seriesData = data.map((item, index) => ({
        name: item.managerName,
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
          text: 'Employees per Manager',
          style: {
            color: '#1E293B',
            fontSize: '18px',
            fontWeight: '600'
          }
        },

        plotOptions: {
          pie: {
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