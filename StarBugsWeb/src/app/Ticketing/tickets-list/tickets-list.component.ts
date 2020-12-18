import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TicketingService } from 'src/app/services/ticketing.service';
import { WorkspaceService } from 'src/app/services/workspace.service';
import { WorkspaceStore } from 'src/app/store/workspace.service';

@Component({
  selector: 'app-tickets-list',
  templateUrl: './tickets-list.component.html',
  styleUrls: ['./tickets-list.component.css']
})
export class TicketsListComponent implements OnInit {
  workspace: any;
  filter: any;
  people: any[];
  severities: string[]= ['Low', 'Medium', 'High', 'Critical', 'GoLive Critical'];
  statusList: string[]= ['NEW', 'OPEN', 'APPROVED', 'REJECTED', 'DUPLICATE', 'POSTPONED', 'EXPORTED', 'IN PROGRESS'];
  projects: any[];
  applications: any[];
  components: any[];
  ticketRoot: any;
  clientId: any;
  tickets: any[];

  constructor(private workspaceStore: WorkspaceStore, private workspaceService: WorkspaceService,
     private ticketService : TicketingService, private route: ActivatedRoute, private router: Router) {
   }
  
  ngOnInit(): void {
    this.clientId = this.route.snapshot.params['clientId'];

    this.route.queryParams.subscribe(params =>{
      this.browse(params);
    });

    this.getWorkspace();
    this.projects = this.workspace?.projects;
    this.getPeople();

    this.ticketRoot = {
      id: '',
      title: '',
      status: '',
      severity: '',
      project: '',
      app: '',
      component: '',
      reporter: '',
      assignee: ''
    };

   

   
  }

  getWorkspace(){
    this.workspace = this.workspaceStore.getWorkspace();
  }

  getPeople(){
    this.workspaceService.loadClientUsers(this.workspace?.clientId).subscribe((people: any)=>{
      this.people = people;
    });
  }

  onProjectChange(e: any){
    this.applications = this.ticketRoot.project.applications;
  }

  onApplicationChange(e: any){
    this.components = this.ticketRoot.app.components;
  }

  applyFilter(){
    this.filter = {
      id: this.ticketRoot.id,
      title: this.ticketRoot.title?.length && this.ticketRoot.title?.length  > 0? "%" + this.ticketRoot.title + "%" : '',
      status: this.ticketRoot.status?.length && this.ticketRoot.status?.length > 0? "%" + this.ticketRoot.status + "%" : '',
      severity: this.ticketRoot.severity?.length && this.ticketRoot.severity?.length > 0? "%" + this.ticketRoot.severity + "%" : '',
      issuerID: this.ticketRoot.reporter?.id,
      assignee: this.ticketRoot.assignee?.id,
      projectID: this.ticketRoot.project?.id,
      app: this.ticketRoot.app?.id,
      component: this.ticketRoot.component?.id
    };

    const queryParams = {};
    for(let [q, v] of Object.entries(this.filter)){
      if(v !== null && v !== "" && typeof(v) != "undefined"){
        queryParams[q] = v;
      }
    }
    this.router.navigate([`client/${this.clientId}/browse`], {queryParams: queryParams});
 
  }

  browse(filter: any){
    this.ticketService.browse(this.clientId, filter).subscribe((tickets: any)=>{
        this.tickets = tickets;
    })
  }
 
}
