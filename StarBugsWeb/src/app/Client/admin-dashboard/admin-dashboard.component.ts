import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  public clientId: string;
  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.clientId = this.route.snapshot.params['id'];
  }

}
