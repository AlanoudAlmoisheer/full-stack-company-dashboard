import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { MatSidenavModule, MatDrawer } from '@angular/material/sidenav';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { EmployeeFormComponent } from './employee-form/employee-form';
import { MatIconModule } from '@angular/material/icon';
import { EmployeeService } from '../../core/services/employee';
import { DepartmentService } from '../../core/services/department';
import { ManagerService } from '../../core/services/manager';
import { ToastrService } from 'ngx-toastr';


export interface EmployeeInterface {
  id: number;
  name: string;
  email: string;
  salary: number;
  hireDate: Date;
  jobTitle: string;
  departmentId: number;
  managerId: number;
  managerName: string;
  managerEmail: string;
  departmentName: string;

}

@Component({
  selector: 'app-employee',
  standalone: true,
  templateUrl: './employee.html',
  styleUrls: ['./employee.scss'],

  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatSidenavModule,
    FormsModule,
    ReactiveFormsModule,
    EmployeeFormComponent,
    RouterModule,
    MatIconModule,
  ],
})

export class EmployeeComponent implements OnInit {

  @ViewChild('drawer') drawer!: MatDrawer;

  displayedColumns: string[] = ['id', 'name', 'email', 'salary', 'hireDate', 'jobTitle', 'departmentName', 'managerName',
    'managerEmail', 'actions'];

  employees: EmployeeInterface[] = [];
  departments: any[] = [];
  managers: any[] = [];


  selectedEmployee: EmployeeInterface | null = null;

  constructor(
    private employeeService: EmployeeService,
    private departmentService: DepartmentService,
    private managerService: ManagerService,
    private toastr: ToastrService) { }



  //fetch all employees
  ngOnInit(): void {
    this.getEmployees()
    this.getDepartments();
    this.getManagers();
  }

  //get Employee Method 
  getEmployees() {
    this.employeeService.getEmployees().subscribe({
      next: (data) => {
        console.log('test', data)
        this.employees = data;
      },
      error: (err) => {

      }
    });
  }
  //get Department Method 
  getDepartments() {
    this.departmentService.getDepartments().subscribe({
      next: (data) => {
        this.departments = data;
      },
      error: (err) => {

      }
    });
  }

  //get Manager Method 
  getManagers() {
    this.managerService.getManagers().subscribe({
      next: (data) => {
        this.managers = data;
      },
      error: (err) => {

      }
    });
  }


  //Save Method (Add or Edit?)
  saveEmployee(emp: EmployeeInterface): void {
    if (emp.id && emp.id > 0) {
      this.updateEmployee(emp);
    } else {
      this.addEmployee(emp);
    }
  }

  // Add Method
  addEmployee(emp: EmployeeInterface): void {
    this.employeeService.addEmployee(emp).subscribe({
      next: (newEmp) => {
        this.employees = [...this.employees, newEmp];
        this.drawer.close();
      },
      error: (err) => console.error('Add failed', err)
    });
  }


  // Update Method
  updateEmployee(emp: EmployeeInterface): void {
    this.employeeService.updateEmployee(emp).subscribe({
      next: (updatedEmp) => {
        const index = this.employees.findIndex(e => e.id === updatedEmp.id);
        if (index !== -1) {
          this.employees = [
            ...this.employees.slice(0, index),
            updatedEmp,
            ...this.employees.slice(index + 1)
          ];
        }
        this.drawer.close();
      },
      error: (err) => console.error('Update failed', err)
    });
  }


  //Delete Method
  deleteEmployee(id: number) {
    this.employeeService.deleteEmployee(id).subscribe({
      next: () => {
        this.getEmployees();
        this.toastr.success('Employee deleted successfully');
      }
    });
  }

  //Drawer Operations

  //Open drawer for adding
  openAdd() {
    this.selectedEmployee = null;
    this.drawer.open();
  }

  // Open drawer for editing
  openEdit(emp: EmployeeInterface) {
    this.selectedEmployee = emp;
    this.drawer.open();
  }
  //close drawer
  closeDrawer() {
    this.drawer.close();
  }
}

