import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule, MatDrawer } from '@angular/material/sidenav';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ManagerFormComponent } from './manager-form/manager-form';
import { MatIconModule } from '@angular/material/icon';
import { ManagerService } from '../../core/services/manager';
import { ToastrService } from 'ngx-toastr';

export interface ManagerInterface {
  id: number;
  name: string;
  email: string;
  salary: number;
  hireDate: Date;
}

@Component({
  selector: 'app-manager',
  standalone: true,
  templateUrl: './manager.html',
  styleUrls: ['./manager.scss'],
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatSidenavModule,
    FormsModule,
    ReactiveFormsModule,
    ManagerFormComponent,
    MatIconModule,
  ]
})

export class ManagerComponent implements OnInit {
  @ViewChild('drawer') drawer!: MatDrawer;

  displayedColumns: string[] = ['id', 'name', 'email', 'salary', 'hireDate', 'actions'];
  managers: ManagerInterface[] = [];

  selectedManager: ManagerInterface | null = null;

  constructor(
    private managerService: ManagerService,
    private toastr: ToastrService
  ) { }


  //fetch all Manager
  ngOnInit(): void {
    this.getManagers();
  }


  // get Method
  getManagers() {
    this.managerService.getManagers().subscribe({
      next: (data) => {
        console.log('test', data)
        this.managers = data;
      },
      error: (err) => {
        console.error(err);
      }
    });

  }

  //Save Method (Add or Edit?)
  saveManager(manager: ManagerInterface): void {
    if (manager.id && manager.id > 0) {
      this.updateManager(manager);
    } else {
      this.addManager(manager);
    }
  }


  //Add Method
  addManager(manager: ManagerInterface): void {
    this.managerService.addManager(manager).subscribe({
      next: (newManager) => { // 
        this.managers = [...this.managers, newManager];
        this.drawer.close();
      },
      error: (err) => console.error('Add failed', err)
    });
  }

  //Edit (update) Method
  updateManager(manager: ManagerInterface): void {
    this.managerService.updateManager(manager).subscribe({
      next: (updatedManager) => {
        const index = this.managers.findIndex(m => m.id === updatedManager.id);

        if (index !== -1) {
          this.managers = [
            ...this.managers.slice(0, index),
            updatedManager,
            ...this.managers.slice(index + 1)
          ];
        }

        this.drawer.close();
      },
      error: (err) => console.error('Update failed', err)
    });
  }

  //Delete Method

  deleteManager(id: number) {
    this.managerService.deleteManager(id).subscribe({
      next: () => {
        this.getManagers();
        this.toastr.success('Manager deleted successfully');
      }
    });
  }

  //Drawer Operations

  // Open drawer for adding
  openAdd(): void {
    this.selectedManager = null;
    this.drawer.open();
  }
  // Open drawer for editing
  openEdit(manager: ManagerInterface): void {
    this.selectedManager = manager;
    this.drawer.open();
  }

  // Close drawer
  closeDrawer(): void {
    this.drawer.close();
  }

}
