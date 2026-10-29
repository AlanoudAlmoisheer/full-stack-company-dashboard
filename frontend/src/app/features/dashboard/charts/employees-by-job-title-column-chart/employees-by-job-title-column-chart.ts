import { Component, OnInit, CUSTOM_ELEMENTS_SCHEMA, ChangeDetectorRef } from '@angular/core';
import * as Highcharts from 'highcharts';
import { EmployeeJobTitleChartService } from '../../services/employees-by-job-title-chart';
import { HighchartsChartComponent } from 'highcharts-angular';
import { CommonModule } from '@angular/common';
import { CHART_PALETTE } from '../chart-palette';

@Component({
  selector: 'app-employees-by-job-title-column-chart',
  standalone: true,
  imports: [HighchartsChartComponent, CommonModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './employees-by-job-title-column-chart.html',
  styleUrls: ['./employees-by-job-title-column-chart.scss'],
})
export class EmployeesByJobTitleColumnChartComponent implements OnInit {

  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {};
  dataLoaded: boolean = false;

  constructor(
    private employeeJobTitleChartService: EmployeeJobTitleChartService,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.employeeJobTitleChartService.getCounts().subscribe(data => {
      console.log(data);
      this.loadChart(data);
    });
  }
  loadChart(data: any[]) {

    const categories = data.map(item => item.jobTitle);
    const seriesValues = data.map(item => item.employeeCount);

    this.chartOptions = {
      chart: {
        type: 'column',
        height: 280,
        backgroundColor: 'transparent'
      },

      title: {
        text: 'Employees by Job Title',
        style: {
          color: '#1E293B',
          fontSize: '18px',
          fontWeight: '600'
        }
      },

      xAxis: {
        categories: categories,
        labels: {
          style: { color: '#64748B' }
        }
      },

      yAxis: {
        title: {
          text: 'Employees',
          style: { color: '#64748B' }
        },
        labels: {
          style: { color: '#64748B' }
        },
        max: Math.max(...seriesValues) + 1
      },

      plotOptions: {
        column: {
          borderRadius: 6,
          borderWidth: 0,
          dataLabels: {
            enabled: true,
            inside: false,
            y: -10,
            style: {
              color: '#1E293B',
              fontSize: '14px',
              fontWeight: '400'
            }
          }
        }
      },

      colors: CHART_PALETTE.sequence,

      series: [{
        name: 'Employees',
        type: 'column',
        data: seriesValues
      } as Highcharts.SeriesColumnOptions]
    };

    this.dataLoaded = true;
    this.cd.detectChanges();
  }
}
