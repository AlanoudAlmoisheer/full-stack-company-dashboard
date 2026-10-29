import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
      providedIn: 'root'
})
export class EmployeeJobTitleChartService {

      private apiUrl = 'http://localhost:9090/employee-jobtitle-stats';

      constructor(private http: HttpClient) { }

      getCounts(): Observable<any[]> {
            return this.http.get<any[]>(this.apiUrl);
      }
}