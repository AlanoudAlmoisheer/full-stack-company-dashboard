import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface ManagerEmployeeData {
  managerName: string;
  employeeCount: number;
}
@Injectable({
  providedIn: 'root'
})

export class ManagerEmployeePieChartService {
  private apiUrl = 'http://localhost:9090/manager-employee-pie';

  constructor(private http: HttpClient) { }


  getData(): Observable<ManagerEmployeeData[]> {
    return this.http.get<ManagerEmployeeData[]>(this.apiUrl);
  }
}