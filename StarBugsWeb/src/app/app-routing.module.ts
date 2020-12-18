import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddEmployeeComponent } from './Client/add-employee/add-employee.component';
import { AdminDashboardComponent } from './Client/admin-dashboard/admin-dashboard.component';
import { ClientProjectComponent } from './Client/client-project/client-project.component';
import { EmployeeListComponent } from './Client/employee-list/employee-list.component';
import { ProjectsComponent } from './Client/projects/projects.component';
import { TicketsComponent } from './Client/tickets/tickets.component';
import { TicketComponent } from './Ticketing/ticket/ticket.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginComponent } from './login/login.component';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { VerifyEmailComponent } from './verify-email/verify-email.component';
import { TicketsListComponent } from './Ticketing/tickets-list/tickets-list.component';
import { HotBugsComponent } from './Ticketing/hot-bugs/hot-bugs.component';
import { AuthGuardGuard } from './auth-guard.guard';

const routes: Routes = [
  {path: "", component: LandingPageComponent},
  {path: "subscribe", component: RegistrationPageComponent},
  {path: "users/verify-email/:token", component: VerifyEmailComponent},
  {path: "login", component: LoginComponent},
  {path: "client/:id/hot-bugs", component: HotBugsComponent, canActivate:[AuthGuardGuard]},
  {path: "client/:id/admin/dashboard", component: AdminDashboardComponent, canActivate:[AuthGuardGuard]},
  {path: "client/:id/admin/employees", component: EmployeeListComponent, canActivate:[AuthGuardGuard]},
  {path: "client/:id/admin/projects", component: ProjectsComponent, canActivate:[AuthGuardGuard]},
  {path: "client/:id/admin/projects/:pid", component: ClientProjectComponent, canActivate:[AuthGuardGuard]},
  {path: "client/:id/admin/tickets", component: TicketsComponent, canActivate:[AuthGuardGuard]},
  {path: "client/:id/admin/employees/add-employee", component: AddEmployeeComponent, canActivate:[AuthGuardGuard]},
  {path: 'users/reset-password', component: ResetPasswordComponent},
  {path: 'client/:clientId/tickets/:ticketId', component: TicketComponent, canActivate:[AuthGuardGuard]},
  {path: 'client/:clientId/browse', component: TicketsListComponent, canActivate:[AuthGuardGuard]}




];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
