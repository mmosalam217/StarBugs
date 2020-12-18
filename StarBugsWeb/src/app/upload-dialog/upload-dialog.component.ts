import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TicketingService } from '../services/ticketing.service';

@Component({
  selector: 'app-upload-dialog',
  templateUrl: './upload-dialog.component.html',
  styleUrls: ['./upload-dialog.component.css']
})
export class UploadDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<UploadDialogComponent>, private ticketService: TicketingService,
     @Inject(MAT_DIALOG_DATA)public data: any) { }
  public attachment: File;
  public attachment_url: string = '';

  ngOnInit(): void {
  }

  upload(){
    const formData = new FormData();
    formData.append("files", this.attachment);
    this.ticketService.uploadAttachment(this.data.id, formData).subscribe((attachments: any)=>{
      console.log(attachments);
      this.dialogRef.close(attachments);
    }, (err: any)=>{
      console.error(err);
    })
  }

  close(){
    this.dialogRef.close();
  }

  preview(e: any){
    if(e.target.files && e.target.files[0]){
      this.attachment = e.target.files[0]; 
      const fileReader = new FileReader();
      fileReader.readAsDataURL(e.target.files[0])
        fileReader.onload = (event: any)=>{
            this.attachment_url = event.target.result;
        }
    }
  }

}
