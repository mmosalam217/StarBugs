import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { WorkspaceStore } from '../store/workspace.service';
import { Workspace } from '../Models/Workspace';

@Injectable({
  providedIn: 'root'
})
export class WorkspaceService {

  private API_URL = 'http://localhost:8080/api/v1/';

  constructor(private http: HttpClient, private workspaceStore: WorkspaceStore) { }


  getUserWorkspace(client_id: string){
    return this.http.get(`${this.API_URL}/workspaces?client_id=${client_id}`);
  }

  loadClientUsers(client_id: string){
    return this.http.get(`${this.API_URL}/core/client/${client_id}/employees`);
  }

  setupWorkspace(client_id: string){
    this.getUserWorkspace(client_id).subscribe((res:any)=>{
        const workspace = new Workspace(res.client.id, res.client.name, res.projects);
        this.workspaceStore.changeWorkspace(workspace);
      
    }, err =>{
      console.error(err);
    })
  }

  
}
