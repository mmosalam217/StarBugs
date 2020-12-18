import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserCredentials } from '../Models/UserCredentials';
import { BehaviorSubject } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { WorkspaceStore } from '../store/workspace.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private API_URL = 'http://localhost:8080/api/v1/core/users'
  private authenticationHolder = new BehaviorSubject(null);
  public authentication = this.authenticationHolder.asObservable();

  constructor(private http: HttpClient, private workspaceStore: WorkspaceStore) { }

  public authenticate(credentials: UserCredentials){
    return this.http.post(`${this.API_URL}/auth/login`, credentials);
  }

  logout(){
    localStorage.removeItem('user');
    sessionStorage.removeItem('workspace');
    this.authenticationHolder.next(null);
    this.workspaceStore.updateStore(null);
  }

  setAuthentication(){
    const user = JSON.parse(localStorage.getItem('user'));
    if (user){
      if(user.token && user.token != ''){
        this.authenticationHolder.next(user);
      }
    }else{
      this.authenticationHolder.next(null);

    }
  }

  isAuthenticated(){
    const user = JSON.parse(localStorage.getItem('user'));
    return  user != null && !this.isTokenExpired(user.token)? true: false;

  }

  isTokenExpired(token: string){
    const jwtHelper = new JwtHelperService();
    return jwtHelper.isTokenExpired(token);
  }

  

  updateAuthentication(auth: any){
    this.authenticationHolder.next(auth);
  }

  getAuthenticated(){
    if(localStorage.getItem('user')){
      return JSON.parse(localStorage.getItem('user'));
    }else{
      return null;
    }
  }

  getAuthToken(){
    if(localStorage.getItem('user')){
      const user = JSON.parse(localStorage.getItem('user'));
      return user.token
    }else{
      return null;
    }
  }

  resetPassword(password: string, passwordConfirmation: string, token: string){
    return this.http.put(`${this.API_URL}/reset-password?token=${token}`, 
    {newPassword: password, newPasswordConfirmation: passwordConfirmation});
  }


}
