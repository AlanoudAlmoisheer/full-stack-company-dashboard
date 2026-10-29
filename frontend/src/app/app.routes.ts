// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';

import { DepartmentComponent } from './features/department/department';
import { DepartmentFormComponent } from './features/department/department-form/department-form';

import { ManagerComponent } from './features/manager/manager';
import { ManagerFormComponent } from './features/manager/manager-form/manager-form';

import { EmployeeComponent } from './features/employee/employee';
import { EmployeeFormComponent } from './features/employee/employee-form/employee-form';

import { NgModule } from '@angular/core';

import { EntityCountsColumnChartComponent } from './features/dashboard/charts/entity-counts-column-chart/entity-counts-column-chart';

export const routes: Routes = [
  {
    path: 'departments', component: DepartmentComponent, children: [
      { path: 'add', component: DepartmentFormComponent },
      { path: 'edit/:id', component: DepartmentFormComponent }
    ]
  },
  {
    path: 'managers', component: ManagerComponent, children: [
      { path: 'add', component: ManagerFormComponent },
      { path: 'edit/:id', component: ManagerFormComponent }
    ]
  },
  {
    path: 'employees', component: EmployeeComponent, children: [
      { path: 'add', component: EmployeeFormComponent },
      { path: 'edit/:id', component: EmployeeFormComponent }
    ]
  },
  { path: '', redirectTo: 'departments', pathMatch: 'full' },
  { path: 'entity-counts-column-chart', component: EntityCountsColumnChartComponent }

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
