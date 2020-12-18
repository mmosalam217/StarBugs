import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/Models/User';
import { WorkspaceService } from 'src/app/services/workspace.service';
import { UserStoreService } from 'src/app/store/user.service';
import { WorkspaceStore } from 'src/app/store/workspace.service';
import { TicketingService } from '../../services/ticketing.service';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Ticket } from '../../Models/Ticket';
import { AuthenticationService } from 'src/app/services/authentication-service.service';

@Component({
  selector: 'app-new-ticket',
  templateUrl: './new-ticket.component.html',
  styleUrls: ['./new-ticket.component.css']
})
export class NewTicketComponent implements OnInit {

  ticketForm: FormGroup = this.fb.group({
    title: [''],
    issuer: [null],
    client: [null],
    project: [null],
    app: [null],
    component: [null],
    assignee: [null],
    status: [''],
    desc: [''],
    severity: ['']
  });
  
  bugCreationFailed: boolean= false;
  auth: any;
  user: User;
  projects: any[];
  apps:any[];
  assigneeList: any[];
  componentList: any[];
  workspace: any;
  severities: string[]= ['Low', 'Medium', 'High', 'Critical', 'GoLive Critical'];
  errorMsg: string;

  constructor(private router: Router, private ticketingService: TicketingService, private workspaceStore: WorkspaceStore, private authService: AuthenticationService,
              private userStore: UserStoreService, private fb: FormBuilder, private workspaceService: WorkspaceService,
              @Inject(MAT_DIALOG_DATA) public data:any, public dialogRef: MatDialogRef<NewTicketComponent>
              ) { }

  ngOnInit(): void {
    this.authService.authentication.subscribe(user=>{
      this.user = user;
      this.initForm();

    })
  }


  initForm(){
    this.workspaceStore.currentWorkspace.subscribe(workspace=>{
      this.workspace = workspace;
      this.projects = workspace.projects.length > 0 ? workspace.projects: [];
      this.workspaceService.loadClientUsers(workspace.clientId).subscribe((users: any)=>{
        this.assigneeList = users;
        this.ticketForm.patchValue({
          issuer: this.user.firstName + ' ' + this.user.lastName ,
          client: workspace.name,
          status: 'NEW'
         });
      });
    })
   
      
  }

  changeProject(project: any){
  
    this.apps = this.ticketForm.get('project').value.applications;
  }

  changeApp(app: any){
    this.componentList =  this.ticketForm.get('app').value.components;
    
  }



  loadAssignees(client_id: string){
   
  }

 

  create(){
    const assignee = {
      assigneeID: this.ticketForm.get('assignee').value.id ,
      assigneeEmail: this.ticketForm.get('assignee').value.username,
      assigneeName: this.ticketForm.get('assignee').value.firstName + ' ' + this.ticketForm.get('assignee').value.lastName
    }
    const {title, project, app, component, status, severity, desc} = this.ticketForm.value;
    const clientID = this.workspace.clientId;
    const clientName = this.workspace.name;
    const  issuer= this.userStore.getCurrentUser();
    const ticket = new Ticket(title, issuer.id, issuer.firstName + ' ' + issuer.lastName,
                              clientID, clientName, project.id,
                              project.name, app?.id, app?.name, 
                              component?.id, component?.name, assignee, status, desc, severity);
    this.ticketingService.createBug(ticket).subscribe((ticket: any)=>{
      console.log(ticket);
      this.dialogRef.close();
      this.router.navigate([`client/${this.workspace.clientId}/tickets/${ticket.id}`]);
      this
    }, err=>{
      console.error(err);
    })
  }

  close(){
    this.dialogRef.close();

  }

}
