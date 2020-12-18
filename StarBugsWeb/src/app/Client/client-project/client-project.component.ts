import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdminService } from 'src/app/services/admin/admin.service';
import { faTrashAlt, faEdit, faPlusSquare } from '@fortawesome/free-regular-svg-icons'
import { MatDialog } from '@angular/material/dialog';
import { ComponentCreatorComponent } from 'src/app/component-creator/component-creator.component';
import { WorkspaceService } from 'src/app/services/workspace.service';

@Component({
  selector: 'app-client-project',
  templateUrl: './client-project.component.html',
  styleUrls: ['./client-project.component.css']
})
export class ClientProjectComponent implements OnInit {

  project_id: string;
  client_id: string;
  project: any;
  rows: string[]=['ID', 'Name', 'Components', 'Versions', 'Actions'];
  newApp: any;
  version: any;
  faTrash = faTrashAlt;
  editIcon = faEdit;
  faPlus = faPlusSquare;

  constructor(private admin: AdminService, private route: ActivatedRoute, private dialog: MatDialog, private WorkspaceService: WorkspaceService) { }

  ngOnInit(): void {
    this.project_id = this.route.snapshot.params['pid'];
    this.client_id = this.route.snapshot.params['id'];
    this.newApp={
      name: ''
    }

    this.version = {
      name: '',
      status: ''
    }
    this.getProject();

  }

  getProject(){
    this.admin.getProjectById(this.project_id).subscribe((proj: any)=>{
      this.project = proj;
      console.log(this.project);
    })
  }

  createApplication(){
    const appDTO ={
      app: this.newApp,
      version: this.version
    }
    this.admin.createApplication(this.client_id, this.project_id, appDTO).subscribe((app: any)=>{
      this.project.applications.push(app);
      this.WorkspaceService.setupWorkspace(this.client_id);
    })
  }

  openComponentCreator(app_id: string){
    const dialogRef= this.dialog.open(ComponentCreatorComponent, {
      closeOnNavigation: true,
      data:{
        app_id: app_id,
        client_id: this.client_id
      }
    })

    dialogRef.afterClosed().subscribe((data:any)=>{
      if(data.success){
        this.project.applications.forEach((a: any, i: number) => {
            if(a.id == data.app.id) this.project.applications[i] = data.app;
        });
        
        this.WorkspaceService.setupWorkspace(this.client_id);

      }
    })
  }


}
