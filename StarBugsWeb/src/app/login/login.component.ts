import { Component, OnInit } from '@angular/core';
import { UserCredentials } from '../Models/UserCredentials';
import { AuthenticationService } from '../services/authentication-service.service';
import { WorkspaceService } from '../services/workspace.service';
import { User } from '../Models/User';
import { Workspace } from '../Models/Workspace';
import { UserStoreService } from '../store/user.service';
import { WorkspaceStore } from '../store/workspace.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthenticationService, private workspaceService: WorkspaceService,
              private userStore: UserStoreService, private workspaceStore: WorkspaceStore , private router: Router) { }
  public credentials: UserCredentials;

  ngOnInit(): void {
    this.credentials = {
      email: '',
      password: ''
    }
  }

  login(){
    this.authService.authenticate(this.credentials).subscribe((res:any)=>{
      const user = new User(res.user.id, res.user.username, res.user.firstName, res.user.lastName, res.token, res.user.authorities, res.user.clientId);
      this.userStore.changeUser(user);
      this.authService.updateAuthentication(user);
      if(user.isAdmin || user.isClient){
        this.router.navigate([`client/${user.clientId}/admin/dashboard`]);
        this.workspaceService.setupWorkspace(user.clientId);
      }else{
        this.router.navigate([`client/${user.clientId}/hot-bugs`]);
        this.workspaceService.setupWorkspace(user.clientId);
      }
    }, (err: any)=>{
      if(err) console.log(err);
    })
  }



}
