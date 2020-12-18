import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminService } from '../services/admin/admin.service';
import { WorkspaceService } from '../services/workspace.service';

@Component({
  selector: 'app-new-project-dialog',
  templateUrl: './new-project-dialog.component.html',
  styleUrls: ['./new-project-dialog.component.css']
})
export class NewProjectDialogComponent implements OnInit {
 
  public project: any;
  public users: any[]=[];
  public project_admin: any;
  public domains: string[] = ['Web Application', 'Mobile Application', 'Desktop Application', 'Android Application', 'iOS Application'];
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private dialog: MatDialogRef<NewProjectDialogComponent>, private admin: AdminService, private workspaceService: WorkspaceService) { }

  ngOnInit(): void {
    this.project = {
      name: '',
      domain: '',
      adminEmail: '',
      companyId: ''
    }

    this.admin.getEmployees(this.data.client_id).subscribe((users: any)=>{
      this.users = users;
    })
  }

  create(){
      this.project.adminEmail = this.project_admin.username;
      this.admin.addProject(this.project, this.data.client_id).subscribe((res: any)=>{
      this.workspaceService.setupWorkspace(this.data.client_id);
          this.dialog.close({
            success: true,
            projects: res.projects
          });
        
       
      }, (err: any) =>{
        console.log(err);
      })
  }

  close(){
    this.dialog.close({
      success: false,
      aborted: true,
      projects: null
    });
  }
}
