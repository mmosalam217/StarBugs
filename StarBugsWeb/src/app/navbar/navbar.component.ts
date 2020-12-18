import { Component, OnInit } from '@angular/core';
import { UserStoreService } from '../store/user.service';
import { AuthenticationService } from '../services/authentication-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { NewTicketComponent } from '../Ticketing/new-ticket/new-ticket.component';
import { faSignOutAlt} from '@fortawesome/free-solid-svg-icons';
import { WorkspaceStore } from '../store/workspace.service';
import { User } from '../Models/User';
import { Workspace } from '../Models/Workspace';
import { Route } from '@angular/compiler/src/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { TicketingService } from '../services/ticketing.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  public isAdmin: boolean = false;
  public isClient: boolean = false;
  public isAuthenticated: boolean = false;
  faSignOut = faSignOutAlt;
  user: any;
  workspace: any;

  searchTerm: string = '';
  searchControl = new FormControl();
  searchCtrlSub: Subscription;

  searchResults: any[]= [];

  constructor(private authService: AuthenticationService, private route: ActivatedRoute, private ticketSerivce: TicketingService,
              private router: Router, private dialog: MatDialog, private workspaceStore: WorkspaceStore) { }

  ngOnInit(): void {

  
    this.init();
    
  }

  init(){
    this.authService.authentication.subscribe((authenticated: User)=>{
      this.isAuthenticated = authenticated != null? true: false;
      if(this.isAuthenticated){
       this.user = authenticated;
       this.isAdmin = authenticated.isAdmin;
       this.isClient = authenticated.isClient;
       this.workspaceStore.currentWorkspace.subscribe((workspace: Workspace)=>{
           this.workspace = workspace;
           this.searchCtrlSub = this.searchControl.valueChanges.pipe(debounceTime(1000), distinctUntilChanged())
           .subscribe((newSearchTerm)=>{
             this.searchTerm = newSearchTerm;
             if(this.searchTerm !== ''){
              this.search(this.searchTerm);
             }
           })
        });
       }

    })
  }

  search(search: string){
    this.ticketSerivce.search(search, this.workspace.clientId).subscribe((result: any)=>{
      this.searchResults = result;
      console.log(result);
    })
  }
  
  logout(){
    this.authService.logout();
    this.router.navigate(['login']);
  }


  openBugDialog(){
    this.dialog.open(NewTicketComponent, {
      closeOnNavigation: true,
      hasBackdrop: true,
      height:"80%",
      position: {
        'top': '6%'
      }
    });
  }

}
