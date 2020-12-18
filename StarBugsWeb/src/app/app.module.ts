import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NavbarComponent } from './navbar/navbar.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddAdminFormComponent } from './Client/add-admin-form/add-admin-form.component';
import { AddProjectFormComponent } from './Client/add-project-form/add-project-form.component';
import { AddEmployeeComponent } from './Client/add-employee/add-employee.component';
import { NewTicketComponent } from './Ticketing/new-ticket/new-ticket.component';
import { TicketsListComponent } from './Ticketing/tickets-list/tickets-list.component';
import { TicketComponent } from './Ticketing/ticket/ticket.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms'
import { MatStepperModule } from "@angular/material/stepper";
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatDialogModule, MAT_DIALOG_DEFAULT_OPTIONS } from '@angular/material/dialog';
import { MatExpansionModule } from '@angular/material/expansion'
import { VerifyEmailComponent } from './verify-email/verify-email.component';
import { AdminDashboardComponent } from './Client/admin-dashboard/admin-dashboard.component';
import { EmployeeListComponent } from './Client/employee-list/employee-list.component';
import { ProjectsComponent } from './Client/projects/projects.component';
import { TicketsComponent } from './Client/tickets/tickets.component';
import { AuthTokenInterceptor } from './interceptors/auth-token-interceptor.interceptor';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { NewProjectDialogComponent } from './new-project-dialog/new-project-dialog.component';
import { DeleteProjectConfirmationComponent } from './delete-project-confirmation/delete-project-confirmation.component';
import { ClientProjectComponent } from './Client/client-project/client-project.component';
import { ComponentCreatorComponent } from './component-creator/component-creator.component';
import { ComponentDeleteConfirmationComponent } from './component-delete-confirmation/component-delete-confirmation.component';
import { AppDeleteConfirmationComponent } from './app-delete-confirmation/app-delete-confirmation.component';
import { UploadDialogComponent } from './upload-dialog/upload-dialog.component';
import { AttachmentViewerComponent } from './attachment-viewer/attachment-viewer.component';
import { TicketEditorComponent } from './Ticketing/ticket-editor/ticket-editor.component';
import { TicketAssignmentDialogComponent } from './ticket-assignment-dialog/ticket-assignment-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { HotBugsComponent } from './Ticketing/hot-bugs/hot-bugs.component';
import { RegistrationSuccessDialogComponent } from './registration-success-dialog/registration-success-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    RegistrationPageComponent,
    LoginComponent,
    DashboardComponent,
    AddAdminFormComponent,
    AddProjectFormComponent,
    AddEmployeeComponent,
    NewTicketComponent,
    TicketsListComponent,
    TicketComponent,
    LandingPageComponent,
    VerifyEmailComponent,
    AdminDashboardComponent,
    EmployeeListComponent,
    ProjectsComponent,
    TicketsComponent,
    ResetPasswordComponent,
    SidebarComponent,
    NewProjectDialogComponent,
    DeleteProjectConfirmationComponent,
    ClientProjectComponent,
    ComponentCreatorComponent,
    ComponentDeleteConfirmationComponent,
    AppDeleteConfirmationComponent,
    UploadDialogComponent,
    AttachmentViewerComponent,
    TicketEditorComponent,
    TicketAssignmentDialogComponent,
    HotBugsComponent,
    RegistrationSuccessDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    MatStepperModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatDialogModule,
    MatExpansionModule,
    MatButtonModule,
    MatAutocompleteModule
    
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthTokenInterceptor, multi: true},
              {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: true}}],
  bootstrap: [AppComponent],
  entryComponents:[NewTicketComponent, NewProjectDialogComponent, ComponentDeleteConfirmationComponent, AttachmentViewerComponent,
     DeleteProjectConfirmationComponent, AppDeleteConfirmationComponent, ComponentCreatorComponent, UploadDialogComponent,
      TicketEditorComponent, RegistrationSuccessDialogComponent]
})
export class AppModule { }
