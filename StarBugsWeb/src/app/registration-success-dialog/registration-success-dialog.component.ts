import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-registration-success-dialog',
  templateUrl: './registration-success-dialog.component.html',
  styleUrls: ['./registration-success-dialog.component.css']
})
export class RegistrationSuccessDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<RegistrationSuccessDialogComponent>) { }
  public successIcon = faCheckCircle;
  
  ngOnInit(): void {
  }

  ok(){
    this.dialogRef.close();
  }

}
