import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { EmployeeInterface } from '../employee';
import { Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, MatButtonModule,   MatFormFieldModule,
    MatSelectModule,
    MatInputModule],
  templateUrl: './employee-form.html',
  styleUrls: ['./employee-form.scss']
})
export class EmployeeFormComponent implements OnChanges {

  @Input() employee: EmployeeInterface | null = null;
  @Input() managers: any[] = []; 
  @Input() departments: any[] = [];
  @Output() close = new EventEmitter<void>();
  @Output() saveEmployee = new EventEmitter<EmployeeInterface>();
  @Output() updateEmployee = new EventEmitter<EmployeeInterface>();

  form: FormGroup;
  isNew: boolean = false
  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      id:this.employee?.id,
      name: [
        '',
        [Validators.required, Validators.minLength(5)]
      ],

      email: [
        '',
        [Validators.required, Validators.email]
      ],

      salary: [
        null,
        [Validators.required, Validators.min(1)]
      ],

      hireDate: [
        null,
        Validators.required
      ],
      jobTitle: [
        '',
        [Validators.required, Validators.minLength(2)]
      ],

    departmentId: [null, Validators.required],

     managerId: [null, Validators.required] 
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.employee) {
      this.isNew = false
      this.form.patchValue(this.employee);
    } else {
      this.isNew = true
      this.form.reset();
    }
  }
  save(): void {
    if (this.form.valid) {
      if (this.isNew) {
        this.saveEmployee.emit(this.form.value);
      } else {
        this.updateEmployee.emit(this.form.value);
      }
      this.close.emit();
    } else {
      this.form.markAllAsTouched();
    }
  }

  cancel(): void {
    this.close.emit();
  }
}

