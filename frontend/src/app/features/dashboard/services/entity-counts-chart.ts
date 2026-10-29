import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
      providedIn: 'root'
})

export class TableCountColumnChartService {
      private apiUrl = 'http://localhost:9090/table-count-column-chart/counts';

      constructor(private http: HttpClient) { }

      getCounts(): Observable<any> {
            return this.http.get(this.apiUrl);
      }
}