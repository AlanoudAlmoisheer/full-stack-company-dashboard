import { Component, OnInit, CUSTOM_ELEMENTS_SCHEMA, ChangeDetectorRef } from '@angular/core';
import * as Highcharts from 'highcharts';
import { TableCountColumnChartService } from '../../services/entity-counts-chart';
import { HighchartsChartComponent } from 'highcharts-angular';
import { CommonModule } from '@angular/common';
import { CHART_PALETTE } from '../chart-palette';


@Component({
  selector: 'app-entity-counts-column-chart',
  standalone: true,
  imports: [HighchartsChartComponent, CommonModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './entity-counts-column-chart.html',
  styleUrls: ['./entity-counts-column-chart.scss'],
})
export class EntityCountsColumnChartComponent implements OnInit {

  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {};
  dataLoaded: boolean = false
  constructor(private tableCountColumnChartService: TableCountColumnChartService,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.tableCountColumnChartService.getCounts().subscribe(data => {
      this.loadChart(data)
    });
  }


  loadChart(data: any) {

    this.chartOptions = {

      chart: {
        type: 'column',
        height: 280,
        backgroundColor: 'transparent'
      },

      title: {
        text: 'Database Table Counts',
        style: {
          color: '#1E293B',
          fontSize: '18px',
          fontWeight: '600'
        }
      },

      xAxis: {
        categories: Object.keys(data),
        labels: {
          style: { color: '#64748B' }
        }
      },

      yAxis: {
        title: {
          text: 'Count',
          style: { color: '#64748B' }
        },
        labels: {
          style: { color: '#64748B' }
        }
      },

      plotOptions: {
        column: {
          borderRadius: 6,
          borderWidth: 0,
          dataLabels: {
            enabled: true,
            inside: false,
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
        name: 'Count',
        type: 'column',
        data: Object.values(data)
      } as Highcharts.SeriesColumnOptions]
    };

    this.dataLoaded = true;
    this.cd.detectChanges();
  }
}


