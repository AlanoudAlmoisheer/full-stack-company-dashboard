import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployeeInterface } from '../../features/employee/employee';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {


 private apiUrl = "http://localhost:9090/Employee";

 constructor(private http: HttpClient)  {}

    //Fetch All Employees
  getEmployees(): Observable<EmployeeInterface[]> {
    return this.http.get<EmployeeInterface[]>(`${this.apiUrl}/all`);
  }


  // Add
  addEmployee(emp: EmployeeInterface): Observable<EmployeeInterface> {
    return this.http.post<EmployeeInterface>(`${this.apiUrl}/add`, emp);
  }

  // Update 
  updateEmployee(emp: EmployeeInterface): Observable<EmployeeInterface> {
    return this.http.put<EmployeeInterface>(`${this.apiUrl}/update/${emp.id}`, emp);
  }

  // Delete 
  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

}
