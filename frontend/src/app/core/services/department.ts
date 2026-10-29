import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DepartmentInterface } from '../../features/department/department';



@Injectable({
  providedIn: 'root',
})
export class DepartmentService {

  private apiUrl = "http://localhost:9090/Department";
  constructor(private http: HttpClient) { }


  //Fetch All Departments
  getDepartments(): Observable<DepartmentInterface[]> {
    return this.http.get<DepartmentInterface[]>(`${this.apiUrl}/all`);
  }

  //Add 
  addDepartment(dept: DepartmentInterface): Observable<DepartmentInterface> {
    return this.http.post<DepartmentInterface>(`${this.apiUrl}/add`, dept);
  }

  //edit
  updateDepartment(dept: DepartmentInterface): Observable<DepartmentInterface> {
    return this.http.put<DepartmentInterface>(`${this.apiUrl}/update/${dept.id}`, dept);
  }

  //delete
  deleteDepartment(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

}

