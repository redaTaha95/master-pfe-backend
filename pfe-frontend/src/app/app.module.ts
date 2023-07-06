import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { LoginComponent } from './core/login/login.component';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
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
import { IndexProjectComponent } from './components/project/index-project/index-project.component';
import { CreateProjectComponent } from './components/project/create-project/create-project.component';
import { ViewProjectComponent } from './components/project/view-project/view-project.component';
import { EditProjectComponent } from './components/project/edit-project/edit-project.component';
import { SelectProjectComponent } from './components/task/select-project/select-project.component';
import { CreateTaskComponent } from './components/task/create-task/create-task.component';
import { IndexTaskComponent } from './components/task/index-task/index-task.component';
import { EditTaskComponent } from './components/task/edit-task/edit-task.component';

const routes: Routes = [
  { path: '', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'employees', component: IndexEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'employees/add', component: CreateEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'employees/:id', component: ViewEmployeesComponent, canActivate: [AuthGuard] },

  { path: 'projects', component: IndexProjectComponent, canActivate: [AuthGuard] },
  { path: 'projects/add', component: CreateProjectComponent, canActivate: [AuthGuard] },
  { path: 'projects/:id', component: ViewProjectComponent, canActivate: [AuthGuard] },
  { path: 'projects/edit/:id', component: EditProjectComponent, canActivate: [AuthGuard] },

  { path: 'tasks', component: SelectProjectComponent, canActivate: [AuthGuard] },
  { path: 'tasks/add/:status/:id', component: CreateTaskComponent, canActivate: [AuthGuard] },
  { path: 'tasks/project/:id', component: IndexTaskComponent, canActivate: [AuthGuard] },
  { path: 'tasks/edit/:id', component: EditTaskComponent, canActivate: [AuthGuard] },


  { path: 'employees/:id/edit', component: EditEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: '**', component: PageNotFoundComponent }
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
    IndexProjectComponent,
    ViewProjectComponent,
    EditProjectComponent,
    CreateProjectComponent,
    SelectProjectComponent,
    CreateTaskComponent,
    IndexTaskComponent,
    EditTaskComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    ToastrModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    DragDropModule
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
