import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegistrationRequest } from '../Models/RegistrationRequest';


@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private API_URL: String = "http://localhost:8080/api/v1/core"
  constructor(public http: HttpClient) { }

  subscribe(registrationRequest: RegistrationRequest){
    return this.http.post(`${this.API_URL}/subscribe`, registrationRequest);

  }

  getSubscriptions(){
    return this.http.get(`${this.API_URL}/subscriptions`);
  }

  verifyEmail(token: string){
    return this.http.post(`${this.API_URL}/users/verify-email/${token}`, {});
  }

}
