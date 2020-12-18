import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TicketingService } from '../../services/ticketing.service';

@Component({
  selector: 'app-hot-bugs',
  templateUrl: './hot-bugs.component.html',
  styleUrls: ['./hot-bugs.component.css']
})
export class HotBugsComponent implements OnInit {

  constructor(private route: ActivatedRoute, private ticketService: TicketingService) { }
  tickets: any;
  clientId: string;

  ngOnInit(): void {
    this.clientId = this.route.snapshot.params['id'];
    this.browse();
  }

  browse(){
    this.ticketService.browse(this.clientId, {status: '%OPEN%'}).subscribe((tickets: any)=>{
        this.tickets = tickets;
    })
  }

}
