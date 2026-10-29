import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DepartmentEmployeeData {
    departmentName: string;
    employeeCount: number;
}

@Injectable({
    providedIn: 'root'
})
export class DepartmentEmployeeDonutService {
    private apiUrl = 'http://localhost:9090/employee-department-count';

    constructor(private http: HttpClient) { }

    getData(): Observable<DepartmentEmployeeData[]> {
        return this.http.get<DepartmentEmployeeData[]>(this.apiUrl);
    }
}