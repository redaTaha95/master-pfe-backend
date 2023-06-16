import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { LoginComponent } from './core/login/login.component';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AuthGuard } from './core/auth.guard';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {NgxWebstorageModule} from "ngx-webstorage";
import {ToastrModule} from "ngx-toastr";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IndexEmployeesComponent } from './components/employee/index-employees/index-employees.component';
import { CreateEmployeesComponent } from './components/employee/create-employees/create-employees.component';
import { EditEmployeesComponent } from './components/employee/edit-employees/edit-employees.component';
import { AppLayoutComponent } from './shared/app-layout/app-layout.component';
import { TokenInterceptor } from './core/token.interceptor';
import { ViewEmployeesComponent } from './components/employee/view-employees/view-employees.component';
import { IndexCandidatesComponent } from './components/candidate/index-candidates/index-candidates.component';
import { CreateCandidateComponent } from './components/candidate/create-candidate/create-candidate.component';
import { EditCandidateComponent } from './components/candidate/edit-candidate/edit-candidate.component';
import { ViewCandidateComponent } from './components/candidate/view-candidate/view-candidate.component';
import { ChatbotComponent } from './components/chatbot/chatbot.component';

const routes: Routes = [
  { path: '', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'employees', component: IndexEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'employees/add', component: CreateEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'employees/:id', component: ViewEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'employees/:id/edit', component: EditEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: '**', component: PageNotFoundComponent },
  { path: 'candidates', component: IndexCandidatesComponent, canActivate: [AuthGuard] },
  { path: 'candidates/add', component: CreateCandidateComponent, canActivate: [AuthGuard] },
  { path: 'candidates/:id', component: ViewCandidateComponent, canActivate: [AuthGuard] },
  { path: 'candidates/:id/edit', component: EditCandidateComponent, canActivate: [AuthGuard] },
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
    LoginComponent,
    DashboardComponent,
    PageNotFoundComponent,
    IndexEmployeesComponent,
    CreateEmployeesComponent,
    EditEmployeesComponent,
    AppLayoutComponent,
    ViewEmployeesComponent,
    IndexCandidatesComponent,
    CreateCandidateComponent,
    EditCandidateComponent,
    ViewCandidateComponent,
    ChatbotComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    ToastrModule.forRoot(),
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide : HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi   : true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
