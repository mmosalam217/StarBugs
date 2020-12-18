import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { AdminService } from '../../services/admin/admin.service';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  clientId: string;
  users: Array<any>;

  
  constructor(private adminService: AdminService, private route: ActivatedRoute) {

   }

  ngOnInit(): void {
    this.clientId = this.route.snapshot.params['id'];

    this.getClientEmployees();
  }

  getClientEmployees(){
    this.adminService.getEmployees(this.clientId).subscribe((res:any)=>{
      this.users = res;
      console.log(this.users)
    })
  }

}
