import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CompanyInformation } from '../Models/CompanyInformation';
import { RegistrationRequest } from '../Models/RegistrationRequest';
import { Rootuser } from '../Models/Rootuser';
import { RegistrationSuccessDialogComponent } from '../registration-success-dialog/registration-success-dialog.component';
import { SubscriptionService } from '../services/subscription.service';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {

  personalInformationForm: FormGroup;
  companyInformationForm: FormGroup;
  subscriptions: any[];
  subscriptionSelected: boolean = false;
  subscription_id: any;
  registrationFailure: boolean = false;
  registrationFailureMsg: string = '';

  public domains: string []= ["Entertainment", "Social Media", "Advertisment", "IT Solutions", "Digital Agency", "Tourism",
   "Sports", "Wears", "Food & Resturants", "Cinemas", "TV"];

  constructor(public fb: FormBuilder, public subscriptionService: SubscriptionService, private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.personalInformationForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      password:['', Validators.required]
    });

    this.companyInformationForm = this.fb.group({
      name: ['', Validators.required],
      region:['', Validators.required],
      domain: ['', Validators.required]
    });

    this.loadSubscriptions();

  }

  subscribe(){
    const user = new Rootuser(this.personalInformationForm.controls.firstName.value,
     this.personalInformationForm.controls.lastName.value,
     this.personalInformationForm.controls.username.value,
     this.personalInformationForm.controls.password.value);

     const company = new CompanyInformation(
      this.companyInformationForm.controls.name.value,
      this.companyInformationForm.controls.region.value,
      this.companyInformationForm.controls.domain.value,

     );
     const registrationRequest = new RegistrationRequest(user, company, this.subscription_id);
     this.subscriptionService.subscribe(registrationRequest).subscribe((res:any)=>{
        const successDialogRef = this.dialog.open(RegistrationSuccessDialogComponent, {
          height: "300px",
          width: "700px"
        });

        successDialogRef.afterClosed().subscribe(()=>{
          this.router.navigate(["/"]);
        })
     }, (err: any)=>{
        this.registrationFailure = true;
     })
  }

  loadSubscriptions(){
    this.subscriptionService.getSubscriptions().subscribe((res:any)=>{
      this.subscriptions = res;
    })
  }

  addPlan(id: any){
    this.subscriptionSelected = true;
    this.subscription_id = id;
  }

  selectPlan(plan:any){
    this.subscriptionSelected = true;
    this.subscription_id = plan.id; 
  }


}
