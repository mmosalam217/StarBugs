import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TicketingService } from 'src/app/services/ticketing.service';
import { faUpload, faUserEdit, faShareSquare, faEye, faHandshake, faUserTie, faUsers } from '@fortawesome/free-solid-svg-icons';
import { MatDialog } from '@angular/material/dialog';
import { UploadDialogComponent } from 'src/app/upload-dialog/upload-dialog.component';
import { UserStoreService } from 'src/app/store/user.service';
import { AttachmentViewerComponent } from 'src/app/attachment-viewer/attachment-viewer.component';
import { TicketEditorComponent } from '../ticket-editor/ticket-editor.component';
import { TicketAssignmentDialogComponent } from 'src/app/ticket-assignment-dialog/ticket-assignment-dialog.component';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute, private userStore: UserStoreService,
     private ticketService: TicketingService, private dialog: MatDialog) { }
  public id : any;
  public ticket: any;
  public uploadIcon = faUpload;
  public editIcon = faUserEdit;
  public assignIcon = faShareSquare;
  public followIcon = faEye;
  public usersIcon = faUsers;
  public assignToMeIcon = faHandshake;
  public followerIcon = faUserTie;
  public newAttachment: File;
  public newComment: any;
  public auth: any;

  ngOnInit(): void {
    this.auth = this.userStore.getCurrentUser();
    this.getTicket();
    this.newComment = {
      commentatorID: '',
      commentatorName: '',
      commentatorEmail: '',
      commentContent: ''
    }
  }

  getTicket(){
    this.id = this.route.snapshot.params['ticketId'];
    this.ticketService.getBug(this.id).subscribe((ticket:any)=>{
      console.log(ticket);
      this.ticket = ticket;
    })
  }

  openUploadDialog(){
    const dialogRef = this.dialog.open(UploadDialogComponent, {
      data:{
        id: this.id
      }
    });
    dialogRef.afterClosed().subscribe((attachments: any)=>{
      if(attachments.length) this.ticket.attachments = attachments;
    })
  }

  addComment(){
    const user = this.userStore.getCurrentUser();
      if(user){
        this.newComment.commentatorID = user.id,
        this.newComment.commentatorName = user.username.substring(0, user.username.indexOf("@")),
        this.newComment.commentatorEmail = user.username
      
        this.ticketService.comment(this.id, this.newComment).subscribe((comments: any)=>{
          this.ticket.comments = comments;
          this.newComment = {
            commentatorID: '',
            commentatorName: '',
            commentatorEmail: '',
            commentContent: ''
          }
        })
      }
     

    
 
  }

  openAttachmentViewer(imgUrl: string){
    this.dialog.open(AttachmentViewerComponent, {
      data:{
        url: imgUrl
      },
      width: "35%",
      height: "70%",
      closeOnNavigation: true
    });
  }

  openDetailsEditor(){
    const dialogRef = this.dialog.open(TicketEditorComponent, {
      data: {
        ticket: this.ticket
      },
      closeOnNavigation: true

    });
    dialogRef.afterClosed().subscribe((ticket: any)=>{
      console.log(ticket)
      if(ticket) this.ticket = ticket;
    })
  }


  openAssignmentDialog(){
   const dialogRef = this.dialog.open(TicketAssignmentDialogComponent, {
     data : {
       id: this.ticket.id,
       assignee: this.ticket.assignee
     },
     width:'30%',
   });

   dialogRef.afterClosed().subscribe((assignment: any)=>{
     if(assignment) this.ticket.assignee = assignment;
   })
  }


  assignToMe(){
      const assignee = {
        assigneeID: this.auth.id,
        assigneeEmail: this.auth.username,
        assigneeName: this.auth.firstName + ' ' + this.auth.lastName
      }
      this.ticketService.assign(this.ticket?.id, assignee).subscribe((assignment: any)=>{
        this.ticket.assignee = assignment;
      }, err =>{
        console.error(err);
      })
  
  }

  follow(){
    const follow = {
      followerID: this.auth.id,
      followerEmail: this.auth.username,
      followerName: this.auth.firstName + ' ' + this.auth.lastName
    }
    this.ticketService.follow(this.ticket.id, follow).subscribe((follows: any)=>{
      this.ticket.follows = follows;
    })
  }

}
