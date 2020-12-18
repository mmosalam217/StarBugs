import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication-service.service';
import { WorkspaceService } from './services/workspace.service';
import { UserStoreService } from './store/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardGuard implements CanActivate {
  constructor(private authService: AuthenticationService, private workspaceService: WorkspaceService, private userStore: UserStoreService, private router: Router){}
  isAuthenticated: boolean = false;

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        if(this.authService.isAuthenticated()){
          this.authService.updateAuthentication(this.authService.getAuthenticated());
          this.workspaceService.setupWorkspace(this.authService.getAuthenticated().clientId);
          this.isAuthenticated = true;
        }else{
            this.isAuthenticated = true;
            this.authService.updateAuthentication(null);
            this.authService.logout();
            this.router.navigate(["/login"]);
        }
        return this.isAuthenticated;
      
  }
  
}
