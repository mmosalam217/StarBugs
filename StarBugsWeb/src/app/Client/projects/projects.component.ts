import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {faTrashAlt } from '@fortawesome/free-regular-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { AdminService } from 'src/app/services/admin/admin.service';
import { MatDialog } from '@angular/material/dialog';
import { NewProjectDialogComponent } from 'src/app/new-project-dialog/new-project-dialog.component';
import { WorkspaceStore } from 'src/app/store/workspace.service';
import { DeleteProjectConfirmationComponent } from 'src/app/delete-project-confirmation/delete-project-confirmation.component';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  constructor(private admin: AdminService, private route: ActivatedRoute, private dialog: MatDialog, private workspaceStore: WorkspaceStore) { }
  projects: any[]=[];
  client_id: string;
  faPlus = faPlus;
  workspace_name: string;
  faTrash = faTrashAlt;

  ngOnInit(): void {
    this.client_id = this.route.snapshot.params['id'];
    this.workspace_name = this.workspaceStore.getWorkspace().name;
    this.getProjectsMap();
  }

  getProjectsMap(){
    this.admin.getProjects(this.client_id).subscribe((res:any)=>{
       this.projects = res;
    }, (err: any)=>{
      console.error(err);
    })
  }

  openNewProjectDialog(){
    const dialogRef = this.dialog.open(NewProjectDialogComponent, {
      hasBackdrop: true,
      closeOnNavigation: true,
      data: {
        client_id: this.client_id
      }
    });

    dialogRef.afterClosed().subscribe((data: any)=>{
    
      if(data.success){
        this.getProjectsMap();
      }
    })
  }


  openDeleteProjectDialog(project_id: string){
    const dialogRef = this.dialog.open(DeleteProjectConfirmationComponent, {
      hasBackdrop: true,
      closeOnNavigation: true,
      data:{
        client_id: this.client_id,
        project_id: project_id
      }
    });
    dialogRef.afterClosed().subscribe((success:any)=>{
      if(success){
        this.projects.forEach((project, index)=>{
          if(project_id == project.id) this.projects.splice(index, 1);
        })
      }
    })
  }

}
