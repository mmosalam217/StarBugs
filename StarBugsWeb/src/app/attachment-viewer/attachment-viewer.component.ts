import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-attachment-viewer',
  templateUrl: './attachment-viewer.component.html',
  styleUrls: ['./attachment-viewer.component.css']
})
export class AttachmentViewerComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dialogRef: MatDialogRef<AttachmentViewerComponent>) { }
  public img_url: string = '';

  ngOnInit(): void {
    this.img_url = this.data.url;
  }

  close(){
    this.dialogRef.close();
  }

}
