import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication-service.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  password: string;
  passwordConfirmation: string;
  token: string;

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthenticationService) { }

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParamMap.get('token');
  }

  resetPassword(){
    this.authService.resetPassword(this.password, this.passwordConfirmation, this.token)
    .subscribe((res:any)=>{
      console.log(res);
      if(res.status == 202){
        this.router.navigate(['login']);
      }
    }, (err: any)=>{
      console.log(err);
    })
  }

}
