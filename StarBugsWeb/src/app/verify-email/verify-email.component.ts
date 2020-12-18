import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { SubscriptionService } from '../services/subscription.service';

@Component({
  selector: 'app-verify-email',
  templateUrl: './verify-email.component.html',
  styleUrls: ['./verify-email.component.css']
})
export class VerifyEmailComponent implements OnInit {
  token: string;
  loading: boolean;
  verificationSuccess: boolean = false;
  verificationFailed : boolean = false;

  constructor(private route: ActivatedRoute, private subscriptionService: SubscriptionService, private router: Router) { }

  ngOnInit(): void {
    this.token = this.route.snapshot.params['token'];
    this.loading = true;
    this.verify();
  }

  verify(){
    this.subscriptionService.verifyEmail(this.token).subscribe((res:any)=>{
        setTimeout(()=>{ 
          this.loading = false;
          this.verificationSuccess = true;
          this.router.navigate(["/login"]);
        }, 3000);
      
    }, err=>{
      console.error(err);
      this.verificationFailed = true;
        setTimeout(()=>{
          this.loading = false;
          this.router.navigate(["/"])
        }, 5000)
      
    })
  }


}
