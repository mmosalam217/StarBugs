import { Component, OnInit, Inject } from '@angular/core';
import {  MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminService } from '../services/admin/admin.service';
import { WorkspaceService } from '../services/workspace.service';

@Component({
  selector: 'app-delete-project-confirmation',
  templateUrl: './delete-project-confirmation.component.html',
  styleUrls: ['./delete-project-confirmation.component.css']
})
export class DeleteProjectConfirmationComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private admin: AdminService,
  private dialogRef: MatDialogRef<DeleteProjectConfirmationComponent>, private workspaceService: WorkspaceService) { }

  ngOnInit(): void {
    
  }

  delete(){
    this.admin.deleteProject(this.data.project_id).subscribe((res:any)=>{
        this.workspaceService.setupWorkspace(this.data.client_id);
        this.dialogRef.close(true);

    })
  }

  close(){
    this.dialogRef.close();
  }

}
