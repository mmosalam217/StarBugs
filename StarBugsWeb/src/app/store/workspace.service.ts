import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Workspace } from '../Models/Workspace';

@Injectable({
  providedIn: 'root'
})
export class WorkspaceStore {
  private workspaceStorage = new BehaviorSubject(null);
  public currentWorkspace = this.workspaceStorage.asObservable();

  constructor() { }

  changeWorkspace(workspace: Workspace){
    sessionStorage.setItem('workspace', JSON.stringify(workspace));
    this.workspaceStorage.next(workspace);
  }

  updateStore(state: any){
    this.workspaceStorage.next(state);
  }

  getWorkspace(){
   return JSON.parse(sessionStorage.getItem('workspace'));
  }
}
