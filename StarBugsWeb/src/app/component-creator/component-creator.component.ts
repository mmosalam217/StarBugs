import { Component, OnInit, Inject, inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminService } from '../services/admin/admin.service';
@Component({
  selector: 'app-component-creator',
  templateUrl: './component-creator.component.html',
  styleUrls: ['./component-creator.component.css']
})
export class ComponentCreatorComponent implements OnInit {

  comp: any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private admin: AdminService,
             private dialogRef: MatDialogRef<ComponentCreatorComponent>) { }

  ngOnInit(): void {
    this.comp = {
      name: '',
      status: ''
    }
  }


  create(){
    this.admin.createComponent(this.data.client_id, this.data.app_id, this.comp).subscribe((app: any)=>{
      this.dialogRef.close({
        success: true,
        app: app
      })
    })
  }

  close(){
    this.dialogRef.close({
      success: false,
      app: null
    })
  }

}
