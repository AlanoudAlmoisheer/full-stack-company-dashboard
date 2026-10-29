import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule, MatDrawer } from '@angular/material/sidenav';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DepartmentFormComponent } from './department-form/department-form';
import { MatIconModule } from '@angular/material/icon';
import { DepartmentService } from '../../core/services/department';
import { ManagerInterface } from '../manager/manager';
import { ManagerService } from '../../core/services/manager';
import { ToastrService } from 'ngx-toastr';


export interface DepartmentInterface {


  id: number;
  departmentName: string;
  location: string;
  managerId: number;
  managerName: string;
  managerEmail: string;

}

@Component({
  selector: 'app-department',
  standalone: true,
  templateUrl: './department.html',
  styleUrls: ['./department.scss'],
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatSidenavModule,
    FormsModule,
    ReactiveFormsModule,
    DepartmentFormComponent,
    MatIconModule,
    MatButtonModule,
  ],

})
export class DepartmentComponent implements OnInit {
  @ViewChild('drawer') drawer!: MatDrawer;


  displayedColumns: string[] = ['id', 'departmentName', 'location', 'managerName',
    'managerEmail', 'actions'];

  departments: DepartmentInterface[] = [];
  managers: ManagerInterface[] = [];



  selectedDepartment: DepartmentInterface | null = null;

  constructor(
    private departmentService: DepartmentService,
    private managerService: ManagerService,
    private toastr: ToastrService
  ) { }

  //fetch all Departments
  ngOnInit(): void {
    this.getDepartments();
    this.getManagers();
  }


  // get Department Method 
  getDepartments() {
    this.departmentService.getDepartments().subscribe({
      next: (data) => {
        console.log('test', data)
        this.departments = data;
      },
      error: (err) => {

      }
    });
  }

  // get Managers Method 
  getManagers() {
    this.managerService.getManagers().subscribe({
      next: (data) => {
        this.managers = data;
      },
      error: (err) => console.error(err)
    });
  }

  //Save Method (Add or Edit?)
  saveDepartment(dept: DepartmentInterface): void {

    if (dept.id && dept.id > 0) {
      this.updateDepartment(dept);
    } else {
      this.addDepartment(dept);
    }

  }

  //Add Method
  addDepartment(dept: DepartmentInterface): void {

    this.departmentService.addDepartment(dept).subscribe({
      next: (newDept: DepartmentInterface) => {

        this.departments = [...this.departments, newDept];

        this.toastr.success('Department added successfully');

        this.drawer.close();
      }
    });

  }


  //Edit (update) Method
  updateDepartment(dept: DepartmentInterface): void {

    this.departmentService.updateDepartment(dept).subscribe({
      next: (updatedDept: DepartmentInterface) => {

        const index = this.departments.findIndex(
          (d: DepartmentInterface) => d.id === updatedDept.id
        );

        if (index !== -1) {

          this.departments = [
            ...this.departments.slice(0, index),
            updatedDept,
            ...this.departments.slice(index + 1)
          ];

        }

        this.drawer.close();
      },
      error: (err: any) => console.error('Update failed', err)
    });

  }

  //Delete Method
  deleteDepartment(id: number): void {

    this.departmentService.deleteDepartment(id).subscribe({
      next: () => {
        this.getDepartments();
        this.toastr.success('Department deleted successfully');
      }
    });

  }
  //Drawer Operations

  //Open drawer for adding
  openAdd(): void {
    this.selectedDepartment = null;
    this.drawer.open();
  }

  // Open drawer for editing
  openEdit(dept: DepartmentInterface): void {
    this.selectedDepartment = dept;
    this.drawer.open();
  }

  //close drawer
  closeDrawer(): void {
    this.drawer.close();
  }
}


