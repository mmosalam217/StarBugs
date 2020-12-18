import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TicketingService } from '../services/ticketing.service';
import { WorkspaceService } from '../services/workspace.service';
import { UserStoreService } from '../store/user.service';
import { WorkspaceStore } from '../store/workspace.service';

@Component({
  selector: 'app-ticket-assignment-dialog',
  templateUrl: './ticket-assignment-dialog.component.html',
  styleUrls: ['./ticket-assignment-dialog.component.css']
})
export class TicketAssignmentDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<TicketAssignmentDialogComponent>, @Inject(MAT_DIALOG_DATA) public data : any,
              private workspaceService: WorkspaceService, private workspaceStore: WorkspaceStore,
              private ticketService: TicketingService, private userStore: UserStoreService) { }

  public assigneeList: any [];
  public currentAssignee: any;
  public newAssignee: any;

  ngOnInit(): void {
    this.currentAssignee = this.data.assignee;
    this.loadUsers();
  }


  loadUsers(){
    const workspace = this.workspaceStore.getWorkspace();
    this.workspaceService.loadClientUsers(workspace.clientId).subscribe((users: any)=>{
      this.assigneeList = users;
     
    });
  }

  assign(){
    const assignee = {
      assigneeID: this.newAssignee.id,
      assigneeEmail: this.newAssignee.username,
      assigneeName: this.newAssignee.firstName + ' ' + this.newAssignee.lastName
    }
    this.ticketService.assign(this.data.id, assignee).subscribe((assignment: any)=>{
      this.dialogRef.close(assignment);
    }, err =>{
      console.error(err);
    })
  }




  close(){
    this.dialogRef.close(null);
  }

}
