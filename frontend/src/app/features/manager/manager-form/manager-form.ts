import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup , Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { ManagerInterface } from '../manager';

@Component({
  selector: 'app-manager-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './manager-form.html',
  styleUrls: ['./manager-form.scss']
})
export class ManagerFormComponent implements OnChanges {
  @Input() manager: ManagerInterface | null = null;   
  @Output() close = new EventEmitter<void>(); 
  @Output() SaveManager = new EventEmitter<ManagerInterface>();
    @Output() editManager = new EventEmitter<ManagerInterface>();

  form: FormGroup;
  isNew:boolean=false
  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
     id: [''],
    name: ['', [Validators.required, Validators.minLength(2)]],
    email: ['', [Validators.required, Validators.email]],
    salary: ['', [Validators.required, Validators.min(1)]],
    hireDate: ['', [Validators.required]]
    });
  }


  ngOnChanges(changes: SimpleChanges): void {
    if (this.manager) {
      this.isNew=false
      this.form.patchValue(this.manager);
    } else {
      this.isNew=true
      this.form.reset();
    }
  }

  save(): void {
    if (this.form.valid) {
      if(this.isNew){
      this.SaveManager.emit(this.form.value);
      }else{
      this.editManager.emit(this.form.value);        
      }
      this.close.emit();
    }
  }

  cancel(): void {
    this.close.emit();
  }
  }
