import { Routes } from '@angular/router';
import { HelpdeskComponent1 } from './components/helpdesk/helpdesk.component';
import { EngageComponent } from './components/engage/engage.component';
import { MyworklifeComponent } from './components/myworklife/myworklife.component';
import { AttendanceManagementComponent } from './components/attendance-management/attendance-management.component';
import { ViewinfoComponent } from './components/viewmyinfo/viewmyinfo.component';
import { EditProfileComponent } from './viewinfoupdate/edit/edit.component';
import { UpdateProfileComponent } from './viewinfoupdate/update/update.component';
import { AttendanceinfoComponent } from './components/attendance/attendanceinfo/attendanceinfo.component';
import { RegularizationPermissionComponent } from './components/attendance/regularization-permission/regularization-permission.component';
import { LeaveApplyComponent } from './components/leave/leave-apply/leave-apply.component';
import { LeaveBalanceComponent } from './components/leave/leave-balance/leave-balance.component';
import { LeaveCalenderComponent } from './components/leave/leave-calender/leave-calender.component';
import { HolidayCalenderComponent } from './components/leave/holiday-calender/holiday-calender.component';
import { NavbarComponent } from './navbar/navbar.component';
import { TodoComponent } from './components/todo/todo.component';
import { SalaryComponent } from './components/salary/salary.component';
import { ReviewComponent } from './components/todo/review/review.component';
import { TasksComponent } from './components/todo/tasks/tasks.component';
import { RegularizationComponent } from './components/todo/review/regularization/regularization.component';
import { RequestHubComponent } from './components/todo/review/request-hub/request-hub.component';
import { ConfirmationComponent } from './components/todo/review/confirmation/confirmation.component';
import { ResignationComponent } from './components/todo/review/resignation/resignation.component';
import { HelpdeskComponent } from './components/todo/review/helpdesk/helpdesk.component';
import { LeaveCompOffComponent } from './components/todo/review/leave-comp-off/leave-comp-off.component';
import { LeaveCancelComponent } from './components/todo/review/leave-cancel/leave-cancel.component';
import { RestrictedHolidayComponent } from './components/todo/review/restricted-holiday/restricted-holiday.component';
import { PayslipsComponent } from './components/salary/payslips/payslips.component';
import { YtdReportsComponent } from './components/salary/ytd-reports/ytd-reports.component';
import { ItStatementComponent } from './components/salary/it-statement/it-statement.component';
import { ItDeclarationComponent } from './components/salary/it-declaration/it-declaration.component';
import { LoansAdvancesComponent } from './components/salary/loans-advances/loans-advances.component';
import { ReimbursementComponent } from './components/salary/reimbursement/reimbursement.component';
import { ProofInvestmentComponent } from './components/salary/proof-investment/proof-investment.component';
import { SalaryRevisionComponent } from './components/salary/salary-revision/salary-revision.component';
import { DocumentCenterComponent } from './components/document-center/document-center.component';
import { WorkflowDeligatesComponent } from './components/workflow-deligates/workflow-deligates.component';
// import { Form16Component } from './components/document-center/form-16/form-16.component';
import { RequesthubComponent1 } from './components/requesthub/requesthub.component';
import { EmployeeProfileComponent } from './components/employee-profile/employee-profile.component';
import { AccountInfoComponent } from './components/account-info/account-info.component';
import { HomeComponent } from './components/home/home.component';
import { FeedbackComponent } from './components/myworklife/feedback/feedback.component';
import { KudosComponent } from './components/myworklife/kudos/kudos.component';
import { AuthenticationComponent } from './components/authentication/authentication.component';
import { ForgetComponent } from './components/authentication/forget/forget.component';
import { MainLayoutComponent } from './components/main-layout/main-layout.component'; // Import MainLayoutComponent
import { PeopleComponent } from './components/people/people.component';
import { ReportsAnalyticsComponent } from './components/reports-analytics/reports-analytics.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { Mainlayout1Component } from './components/admin-dashboard/mainlayout1/mainlayout1.component';
import { RegisterComponent } from './components/admin-dashboard/component/register/register.component';
import { AddEmployeeComponent } from './components/admin-dashboard/component/admin-employee/add-employee/add-employee.component';
import { EmployeeListComponent } from './components/admin-dashboard/component/admin-employee/employee-list/employee-list.component';
import { BankingComponent } from './components/admin-dashboard/component/admin-employee/banking/banking.component';
import { DepartmentComponent } from './components/admin-dashboard/component/department/department.component';
import { SalaryStructureComponent } from './components/admin-dashboard/component/admin-payroll/salary-structure/salary-structure.component';
import { PayrollProcessingComponent } from './components/admin-dashboard/component/admin-payroll/payroll-processing/payroll-processing.component';
import { RegularizationRequestComponent } from './components/admin-dashboard/component/admin-attendance/regularization-request/regularization-request.component';
import { AttendanceReportComponent } from './components/admin-dashboard/component/admin-attendance/attendance-report/attendance-report.component';
import { LeaveTypeComponent } from './components/admin-dashboard/component/admin-leave/leave-type/leave-type.component';
import { LeaveManagementComponent } from './components/admin-dashboard/component/admin-leave/leave-management/leave-management.component';
// import { BenefitsComponent } from './components/admin-dashboard/component/benefits/benefits.component';
// import { Homepage1Component } from './components/admin-dashboard/component/homepage1/homepage1.component';
import { AdminHomeComponent } from './components/admin-dashboard/component/admin-home/admin-home.component';
import { EditEmployeeComponent } from './components/admin-dashboard/component/admin-employee/edit-employee/edit-employee.component';
import { ComplianceComponent } from './components/admin-dashboard/component/compliances/compliances.component';
import { AttendanceClockinComponent } from './components/admin-dashboard/component/attendance-clockin/attendance-clockin.component';
import { AttendanceClockoutComponent } from './components/admin-dashboard/component/attendance-clockout/attendance-clockout.component';
import { PerformanceDashboardComponent } from './components/admin-dashboard/component/performance-management/performance-dashboard/performance-dashboard.component';
import { PerformanceReviewComponent } from './components/admin-dashboard/component/performance-management/performance-review/performance-review.component';
import { KpiMasterComponent } from './components/admin-dashboard/component/performance-management/kpi-master/kpi-master.component';
import { FeedbackPageComponent } from './components/admin-dashboard/component/performance-management/feedback-page/feedback-page.component';
import { LeaveReportComponent } from './components/admin-dashboard/component/report-management/leave-report/leave-report.component';
import { ComplianceReportComponent } from './components/admin-dashboard/component/report-management/compliance-report/compliance-report.component';
import { EmployeemasterReportComponent } from './components/admin-dashboard/component/report-management/employeemaster-report/employeemaster-report.component';
// import { AnnouncementManagementComponent } from './components/admin-dashboard/component/announcement-management/announcement-management.component';
import { TaxManagementComponent } from './components/admin-dashboard/component/tax-management/tax-management.component';
import { EmployeeTaxDetailsComponent } from './components/admin-dashboard/component/employee-tax-details/employee-tax-details.component';
import { TimesheetManagementComponent } from './components/admin-dashboard/component/timesheet-management/timesheet-management.component';
import { BenefitMasterComponent } from './components/admin-dashboard/component/benefits/benefit-master/benefit-master.component';
import { BenefitProviderComponent } from './components/admin-dashboard/component/benefits/benefit-provider/benefit-provider.component';
import { BenefitMappingComponent } from './components/admin-dashboard/component/benefits/benefit-mapping/benefit-mapping.component';
import { ShiftAssignmentComponent } from './components/admin-dashboard/component/shift/shift-assignment/shift-assignment.component';
import { ShiftMasterComponent } from './components/admin-dashboard/component/shift/shift-master/shift-master.component';
import { CompliancesAlertComponent } from './components/admin-dashboard/component/admin-compliances/compliances-alert/compliances-alert.component';
import { CompliancesDocumentsComponent } from './components/admin-dashboard/component/admin-compliances/compliances-documents/compliances-documents.component';
import { CompliancesMappingComponent } from './components/admin-dashboard/component/admin-compliances/compliances-mapping/compliances-mapping.component';
import { AdminHolidaysCalendarComponent } from './components/admin-dashboard/component/admin-holidays-calendar/admin-holidays-calendar.component';
import { AnnouncementComponent } from './components/admin-dashboard/component/admin-announcement/admin-announcement.component';
import { AdminDocumentComponent } from './components/admin-dashboard/component/admin-document/admin-document.component';
// import { AdminAnnouncementReadStatusComponent } from './components/admin-dashboard/component/admin-announcement-read-status/admin-announcement-read-status.component';
import { EmployeeAnnouncementComponent } from './components/employee-announcement/employee-announcement.component';
// import { AttendanceComponent } from './components/admin-dashboard/component/admin-attendance/attendance/attendance.component';

export const routes: Routes = [


  // 2️⃣ Authentication routes
  { path: 'authentication', component: AuthenticationComponent },
  { path: 'authentication/forget', component: ForgetComponent },
  { path: 'admin_dashboard', component: AdminDashboardComponent },
  // { path: 'Mainlayout1', component: Mainlayout1Component },

  {
    path: 'admin',
    component: Mainlayout1Component,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: AdminHomeComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'employee-tax-details', component: EmployeeTaxDetailsComponent },
      // EMPLOYEE
      { path: 'employee/add-employee', component: AddEmployeeComponent },
      { path: 'employee/employee-list', component: EmployeeListComponent },
      { path: 'employee/edit-employee/:id', component: EditEmployeeComponent },

      // BANKING
      { path: 'banking', component: BankingComponent },

      // DEPARTMENT
      { path: 'department', component: DepartmentComponent },

      { path: 'announcement', component: AnnouncementComponent },

      //attendance clock in clock out
      { path: 'AttendanceClockin', component: AttendanceClockinComponent },
      { path: 'AttendanceClockout', component: AttendanceClockoutComponent },

      // PAYROLL
      { path: 'payroll/salary-structure', component: SalaryStructureComponent },
      { path: 'payroll/payroll-processing', component: PayrollProcessingComponent },

      // ATTENDANCE
      { path: 'attendance/regularization-request', component: RegularizationRequestComponent },
      { path: 'attendance/attendance-report', component: AttendanceReportComponent },

      // LEAVE
      { path: 'leave/leave-type', component: LeaveTypeComponent },
      { path: 'leave/leave-management', component: LeaveManagementComponent },
      { path: 'shift/shift-master', component: ShiftMasterComponent },
      { path: 'shift/shift-assignment', component: ShiftAssignmentComponent },


      { path: 'compliances/add-compliance', component: ComplianceComponent },
      { path: 'compliances/compliance-mapping', component: CompliancesMappingComponent },
      { path: 'documents', component: AdminDocumentComponent },
      { path: 'compliances/compliance-alerts', component: CompliancesAlertComponent },


      // BENEFITS + COMPLIANCES
      { path: 'benefits/benefit-master', component: BenefitMasterComponent },
      { path: 'benefits/benefit-provider', component: BenefitProviderComponent },
      { path: 'benefits/benefit-mapping', component: BenefitMappingComponent },
      { path: 'compliances', component: ComplianceComponent },
      { path: 'holidays-calendar', component: AdminHolidaysCalendarComponent },
      { path: 'feedback', component: FeedbackPageComponent },
      { path: 'performance-dashboard', component: PerformanceDashboardComponent },
      { path: 'performance-review', component: PerformanceReviewComponent },
      { path: 'kpi-master', component: KpiMasterComponent },

      { path: 'leave-report', component: LeaveReportComponent },
      { path: 'compliance-report', component: ComplianceReportComponent },
      { path: 'attendance-report', component: AttendanceReportComponent },
      { path: 'employeeMaster-report', component: EmployeemasterReportComponent },
      { path: 'tax-management', component: TaxManagementComponent },
      { path: 'timesheet-management', component: TimesheetManagementComponent },
    ]

  },


  {
    path: 'app',
    component: MainLayoutComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent },
      { path: 'engage', component: EngageComponent },


      {
        path: 'announcements',
        component: EmployeeAnnouncementComponent
      }
      ,
      { path: 'myworklife', component: MyworklifeComponent },
      { path: 'feedback', component: FeedbackComponent },
      { path: 'kudos', component: KudosComponent },
      { path: 'AccountInfo', component: AccountInfoComponent },
      { path: 'employee-profile', component: EmployeeProfileComponent },
      { path: 'viewinfo', component: ViewinfoComponent },
      { path: 'leave/apply', component: LeaveApplyComponent },
      { path: 'leave/balance', component: LeaveBalanceComponent },
      { path: 'leave/calendar', component: LeaveCalenderComponent },
      { path: 'holiday', component: HolidayCalenderComponent },
      { path: 'navbar', component: NavbarComponent },
      { path: 'WorkFlow-Deligates', component: WorkflowDeligatesComponent },
      { path: 'edit', component: EditProfileComponent },
      { path: 'document-center', component: DocumentCenterComponent },
      { path: 'Requesthub1', component: RequesthubComponent1 },
      { path: 'update', component: UpdateProfileComponent },
      { path: 'Helpdesk1', component: HelpdeskComponent1 },
      { path: 'timesheet-management', component: TimesheetManagementComponent },
      {
        path: 'attendance-management',
        component: AttendanceManagementComponent,
        children: [
          { path: '', redirectTo: 'attendance-info', pathMatch: 'full' },
          { path: 'attendance-info', component: AttendanceinfoComponent },
          { path: 'regularization-permission', component: RegularizationPermissionComponent },
        ],
      },

      {
        path: 'todo',
        component: TodoComponent,
        children: [
          { path: 'tasks', component: TasksComponent },
          {
            path: 'review',
            component: ReviewComponent,
            children: [
              { path: 'regularization', component: RegularizationComponent },
              { path: 'request-hub', component: RequestHubComponent },
              { path: 'confirmation', component: ConfirmationComponent },
              { path: 'resignation', component: ResignationComponent },
              { path: 'helpdesk', component: HelpdeskComponent },
              { path: 'leave', component: LeaveCompOffComponent },
              { path: 'leave-cancel', component: LeaveCancelComponent },
              { path: 'leave-comp-off', component: LeaveCompOffComponent },
              { path: 'restricted-holiday', component: RestrictedHolidayComponent },
              { path: '', redirectTo: 'regularization', pathMatch: 'full' },
            ],
          },
        ],
      },


      { path: 'people', component: PeopleComponent },
      { path: 'workflow-delegates', component: WorkflowDeligatesComponent },
      { path: 'reports', component: ReportsAnalyticsComponent },
      {
        path: 'salary',
        component: SalaryComponent,
        children: [
          { path: 'payslips', component: PayslipsComponent },
          { path: 'Ytd-reports', component: YtdReportsComponent },
          { path: 'it-statements', component: ItStatementComponent },
          { path: 'it-declaration', component: ItDeclarationComponent },
          { path: 'loan-advances', component: LoansAdvancesComponent },
          { path: 'reimbursement', component: ReimbursementComponent },
          { path: 'proof-investment', component: ProofInvestmentComponent },
          { path: 'salary-revision', component: SalaryRevisionComponent },
        ],
      },
    ],
  },

  // 4️⃣ Wildcard route
  { path: '**', redirectTo: 'authentication', pathMatch: 'full' },
];
