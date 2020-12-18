import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TicketingService {

  private API_URL : string = 'http://localhost:8080/api/v1/tickets';
  constructor(private http: HttpClient) { }

  createBug(bugData: any){
    return this.http.post(`${this.API_URL}/add-ticket`, bugData);

  }

  getBug(id: Number){
    return this.http.get(`${this.API_URL}/${id}`);
  }

  uploadAttachment(id:any, files: FormData){
  return this.http.post(`${this.API_URL}/${id}/add-attachments`, files);

  }

  comment(id: any, comment: any){
  return this.http.post(`${this.API_URL}/${id}/add-comment`, comment);
  }

  updateTicket(id: any, updatedDetails: any){
  return this.http.put(`${this.API_URL}/${id}`, updatedDetails);
  }

  assign(ticket_id: any, assignee: any){
  return this.http.put(`${this.API_URL}/${ticket_id}/assign`, assignee);
  }

  follow(id: any, follow: any){
  return this.http.post(`${this.API_URL}/${id}/follow-ticket`, follow);
  }

  browse(client_id: any, filter: any){
    return this.http.get(`${this.API_URL}/clients/${client_id}/browse`, {params: filter});
  }

  search(keyword: string, client_id: string){
    return this.http.get(`${this.API_URL}/clients/${client_id}/search`, {
      params: { s: keyword}
    });
  }

}
