import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatTabsModule } from '@angular/material/tabs';
import { CommonModule } from '@angular/common';
import { EmployeeComponent } from './features/employee/employee';
import { DepartmentComponent } from './features/department/department';
import { ManagerComponent } from './features/manager/manager';
import { EntityCountsColumnChartComponent } from './features/dashboard/charts/entity-counts-column-chart/entity-counts-column-chart';
import { ManagerEmployeePieChartComponent } from './features/dashboard/charts/employees-by-manager-pie-chart/employees-by-manager-pie-chart';
import { TabState } from './core/services/tab-state.ts';
import { EmployeesByDepartmentDonutChartComponent } from './features/dashboard/charts/employees-by-department-donut-chart/employees-by-department-donut-chart';
import { EmployeesByJobTitleColumnChartComponent } from './features/dashboard/charts/employees-by-job-title-column-chart/employees-by-job-title-column-chart';
import { HeaderComponent } from './layout/header/header';



@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    MatTabsModule,
    EmployeeComponent,
    DepartmentComponent,
    ManagerComponent,
    EntityCountsColumnChartComponent,
    ManagerEmployeePieChartComponent,
    EmployeesByDepartmentDonutChartComponent,
    EmployeesByJobTitleColumnChartComponent,
    HeaderComponent,
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.scss']
})
export class App {

  constructor(private router: Router, private generalService: TabState) { }

  ngOnInit() {
    console.log(this.generalService.currentSelectedTab);
  }

  onTabChange(event: any) {
    this.generalService.currentSelectedTab = event.index;
  }


}
