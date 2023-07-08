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
import { IndexPayrollsComponent } from './components/payroll/index-payrolls/index-payrolls.component';
import { SelectEmployeeComponent } from './components/payroll/select-employee/select-employee.component';
import { CreatePayrollComponent } from './components/payroll/create-payroll/create-payroll.component';
import { ViewPayrollComponent } from './components/payroll/view-payroll/view-payroll.component';
import { EditPayrollComponent } from './components/payroll/edit-payroll/edit-payroll.component';
import { IndexPayslipComponent } from './components/payslip/index-payslip/index-payslip.component';
import { ViewPayslipComponent } from './components/payslip/view-payslip/view-payslip.component';
import { IndexPaidTimeOffComponent } from './components/paidTimeOff/index-paid-time-off/index-paid-time-off.component';
import { SelectEmployeePaidTimeOffComponent } from './components/paidTimeOff/select-employee-paid-time-off/select-employee-paid-time-off.component';
import { CreatePaidTimeOffComponent } from './components/paidTimeOff/create-paid-time-off/create-paid-time-off.component';
import { ViewPaidTimeOffComponent } from './components/paidTimeOff/view-paid-time-off/view-paid-time-off.component';
import { EditPaidTimeOffComponent } from './components/paidTimeOff/edit-paid-time-off/edit-paid-time-off.component';
import { IndexBenifitsComponent } from './components/benifit/index-benifits/index-benifits.component';
import { CreateBenifitsComponent } from './components/benifit/create-benifits/create-benifits.component';
import { ViewBenifitsComponent } from './components/benifit/view-benifits/view-benifits.component';
import { EditBenifitsComponent } from './components/benifit/edit-benifits/edit-benifits.component';
import { CreateBenifitTypeComponent } from './components/benifit/create-benifit-type/create-benifit-type.component';
import { ChatbotComponent } from './components/chatbot/chatbot.component';
import { ViewQuestionsComponent } from './components/chatbot/questions-management/view-questions/view-questions.component';
import { CreateQuestionsComponent } from './components/chatbot/questions-management/create-questions/create-questions.component';
import { UpdateQuestionsComponent } from './components/chatbot/questions-management/update-questions/update-questions.component';
import { IndexQuestionsComponent } from './components/chatbot/questions-management/index-questions/index-questions.component';
import { NgxPaginationModule } from 'ngx-pagination';

const routes: Routes = [
  { path: '', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'employees', component: IndexEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'employees/add', component: CreateEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'employees/:id', component: ViewEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'employees/:id/edit', component: EditEmployeesComponent, canActivate: [AuthGuard] },
  { path: 'chatbot', component: IndexQuestionsComponent, canActivate: [AuthGuard] },
  { path: 'questions/add', component: CreateQuestionsComponent, canActivate: [AuthGuard] },
  { path: 'questions/:id', component: ViewQuestionsComponent, canActivate: [AuthGuard] },
  { path: 'questions/:id/edit', component: UpdateQuestionsComponent, canActivate: [AuthGuard] },

  { path: 'payrolls', component: IndexPayrollsComponent, canActivate: [AuthGuard] },
  { path: 'payrolls/add', component: SelectEmployeeComponent, canActivate: [AuthGuard] },
  { path: 'payrolls/add/:id', component: CreatePayrollComponent, canActivate: [AuthGuard] },
  { path: 'payrolls/:id', component: ViewPayrollComponent, canActivate: [AuthGuard] },
  { path: 'payrolls/:id/edit', component: EditPayrollComponent, canActivate: [AuthGuard] },

  { path: 'payslips/payroll/:id', component: IndexPayslipComponent, canActivate: [AuthGuard] },
  { path: 'payslips/:id', component: ViewPayslipComponent, canActivate: [AuthGuard] },

  { path: '', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'paidTimesOff', component: IndexPaidTimeOffComponent, canActivate: [AuthGuard] },
  { path: 'paidTimesOff/add', component: SelectEmployeePaidTimeOffComponent, canActivate: [AuthGuard] },
  { path: 'paidTimesOff/add/:id', component: CreatePaidTimeOffComponent, canActivate: [AuthGuard] },
  { path: 'paidTimesOff/:id', component: ViewPaidTimeOffComponent, canActivate: [AuthGuard] },
  { path: 'paidTimesOff/edit/:id', component: EditPaidTimeOffComponent, canActivate: [AuthGuard] },

  { path: '', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'benifits', component: IndexBenifitsComponent, canActivate: [AuthGuard] },
  { path: 'benifits/add/:id', component: CreateBenifitsComponent, canActivate: [AuthGuard] },
  { path: 'benifits/:id', component: ViewBenifitsComponent, canActivate: [AuthGuard] },
  { path: 'benifits/edit/:id', component: EditBenifitsComponent, canActivate: [AuthGuard] },
  { path: 'typeValidations/add', component: CreateBenifitTypeComponent, canActivate: [AuthGuard] },

  { path: 'login', component: LoginComponent },
  { path: '**', component: PageNotFoundComponent },
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
    ChatbotComponent,
    ViewQuestionsComponent,
    CreateQuestionsComponent,
    UpdateQuestionsComponent,
    IndexQuestionsComponent,
    IndexPayrollsComponent,
    CreatePayrollComponent,
    EditPayrollComponent,
    ViewPayrollComponent,
    IndexPayslipComponent,
    SelectEmployeeComponent,
    ViewPayslipComponent,
    IndexPaidTimeOffComponent,
    ViewPaidTimeOffComponent,
    EditPaidTimeOffComponent,
    CreatePaidTimeOffComponent,
    SelectEmployeePaidTimeOffComponent,
    IndexBenifitsComponent,
    EditBenifitsComponent,
    ViewBenifitsComponent,
    CreateBenifitsComponent,
    CreateBenifitTypeComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    ToastrModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule
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
