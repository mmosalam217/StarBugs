import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private API_URL = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) { }

  public getEmployees(clientId: string){
    return this.http.get(`${this.API_URL}/core/client/${clientId}/employees`);
  }

  public getProjects(client_id: string){
    return this.http.get(`${this.API_URL}/workspaces/projects?client_id=${client_id}`);
  }

  public addEmployee(client_id: string, employee: any){
    return this.http.post(`${this.API_URL}/core/client/${client_id}/add-employee`, employee);
  }

  public addProject(project: any, client_id: string){
    return this.http.post(`${this.API_URL}/workspaces/projects?client_id=${client_id}`, project);
  }

  public deleteProject(project_id: string){
    return this.http.delete(`${this.API_URL}/workspaces/projects/${project_id}`);
  }

  public getProjectById(id: string){
    return this.http.get(`${this.API_URL}/workspaces/projects/${id}`);

  }

  public createApplication(client_id: string, project_id: string, app: any){
    return this.http.post(`${this.API_URL}/workspaces/projects/${project_id}/applications`, app);

  }

  public createComponent(client_id: string, app_id: string, comp: any){
    return this.http.post(`${this.API_URL}/workspaces/projects/applications/${app_id}/components`, comp);
  }
  
}
