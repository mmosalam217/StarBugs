import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Ticket } from '../../Models/Ticket';
import { TicketingService } from '../../services/ticketing.service';
import { WorkspaceStore } from '../../store/workspace.service';

@Component({
  selector: 'app-ticket-editor',
  templateUrl: './ticket-editor.component.html',
  styleUrls: ['./ticket-editor.component.css']
})
export class TicketEditorComponent implements OnInit {

  constructor(private fb : FormBuilder, private workspaceStore: WorkspaceStore, private dialogRef: MatDialogRef<TicketEditorComponent>,
     @Inject(MAT_DIALOG_DATA) public data: any, private ticketService: TicketingService) { }


  projects: any[];
  apps:any[];
  assigneeList: any[];
  componentList: any[];
  workspace: any;
  severities: string[]= ['Low', 'Medium', 'High', 'Critical', 'GoLive Critical'];
  statusList: any[]= ['NEW', 'OPEN', 'APPROVED', 'REJECTED', 'DUPLICATE', 'POSTPONED', 'EXPORTED', 'IN PROGRESS'];

  updateTicketDetailsForm: FormGroup = this.fb.group({
    title: [''],
    project: [null],
    app: [null],
    component: [null],
    status: [''],
    desc: [''],
    severity: ['']
  });

  ngOnInit(): void {
    this.initForm();
  }

  initForm(){
    const workspace = this.workspaceStore.getWorkspace();
      this.workspace = workspace;
      this.projects = workspace.projects.length > 0 ? workspace.projects: [];
      this.setCurrentTicketDetails(this.data.ticket, this.workspace);
      
  }

  setCurrentTicketDetails(ticket: any, workspace: any){
    const project = workspace?.projects?.filter((proj: any) => proj.id == ticket.projectID)[0];
    this.apps = project?.applications;
    const app = project?.applications?.filter((app:any)=> app.id == ticket.app)[0];
    this.componentList = app?.components;
    const component = app?.components?.filter((comp: any)=> comp.id == ticket.component)[0];

    this.updateTicketDetailsForm.patchValue({
      title: ticket.title,
      project: project,
      app: app,
      component: component,
      status: ticket.status,
      desc: ticket.desc,
      severity: ticket.severity
     });
  }


  changeProject(project: any){
  
    this.apps = this.updateTicketDetailsForm.get('project').value.applications;
  }

  changeApp(app: any){
    this.componentList =  this.updateTicketDetailsForm.get('app').value.components;
    
  }

  update(){
    const {title, project, app, component, status, severity, desc} = this.updateTicketDetailsForm.value;
    const updatedTicket = new Ticket(title, null, null,
      null, null, project.id,
      project.name, app.id, app.name, 
      component.id, component.name, null, status, desc, severity);
      this.ticketService.updateTicket(this.data.ticket.id, updatedTicket).subscribe((updated: any)=>{
        this.dialogRef.close(updated);
      }, err =>{
        console.error(err);
      })

  }

}
