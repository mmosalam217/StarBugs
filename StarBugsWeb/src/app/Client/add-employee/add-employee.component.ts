import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin/admin.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent implements OnInit {

  clientId: string;


  employee: any;
  role: any
  constructor(private route: ActivatedRoute, private admin: AdminService, private router: Router) { }

  ngOnInit(): void {
    this.clientId = this.route.snapshot.params['id'];
    this.employee = {
      firstName: '',
      lastName: '',
      username: '',
      role: ''
    };

  }

  addEmployee(){
    
    this.admin.addEmployee(this.clientId, this.employee).subscribe((res:any)=>{
      if(res.status == 202) this.router.navigate([`/client/${this.clientId}/admin/employees`]);
      console.log(res);
    }, (err: any)=>{
      console.log(err);
    })
  }

  

}
