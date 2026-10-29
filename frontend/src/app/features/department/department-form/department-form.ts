import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { DepartmentInterface } from '../department';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';


@Component({
  selector: 'app-department-form',
  standalone: true,
  templateUrl: './department-form.html',
  styleUrls: ['./department-form.scss'],
  imports: [CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatSelectModule,
    MatFormFieldModule],
})
export class DepartmentFormComponent implements OnChanges {
  @Input() department: DepartmentInterface | null = null;
  @Input() managers: any[] = [];
  @Output() close = new EventEmitter<void>();
  @Output() saveDepartment = new EventEmitter<DepartmentInterface>();


  //Validators
  form: FormGroup;
  isNew: boolean = false
  constructor(private fb: FormBuilder,) {
    this.form = this.fb.group({
      id: [''],
      departmentName: ['', [Validators.required, Validators.minLength(3)]],
      location: ['', [Validators.required, Validators.minLength(3)]
      ],
      managerId: [
        null,
        [Validators.required]
      ]
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.department) {
      this.isNew = false
      this.form.patchValue(this.department);
    } else {
      this.isNew = true
      this.form.reset();
    }
  }


  //save method
  save(): void {
    if (this.form.valid) {

      this.saveDepartment.emit(this.form.value);
      this.form.reset()
    } else {
      this.form.markAllAsTouched();
    }
  }

  //cancel
  cancel(): void {
    this.form.reset()
    this.close.emit();
  }
}