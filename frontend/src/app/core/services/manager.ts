import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ManagerInterface } from '../../features/manager/manager';

@Injectable({
  providedIn: 'root',
})
export class ManagerService {

  private apiUrl = "http://localhost:9090/Manager";

  constructor(private http: HttpClient) { }

  //Fetch All Managers
  getManagers(): Observable<ManagerInterface[]> {
    return this.http.get<ManagerInterface[]>(`${this.apiUrl}/all`);
  }

  //Add
  addManager(dept: ManagerInterface): Observable<ManagerInterface> {
    return this.http.post<ManagerInterface>(`${this.apiUrl}/add`, dept);

  }
  //Update
  updateManager(manager: ManagerInterface): Observable<ManagerInterface> {
    return this.http.put<ManagerInterface>(`${this.apiUrl}/update/${manager.id}`, manager);
  }

  //Delete
  deleteManager(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

}
