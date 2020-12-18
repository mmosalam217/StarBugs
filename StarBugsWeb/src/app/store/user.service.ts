import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserStoreService {

  private userStorage = new BehaviorSubject(null);
  public currentUser = this.userStorage.asObservable();

  constructor() { }

  public changeUser(user:any){
    localStorage.setItem('user', JSON.stringify(user));
    this.userStorage.next(user);
  }

  getCurrentUser(){
    return JSON.parse(localStorage.getItem('user'));
  }
  
}
