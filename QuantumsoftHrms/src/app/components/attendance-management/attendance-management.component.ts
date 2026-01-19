import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AttendanceinfoComponent } from '../attendance/attendanceinfo/attendanceinfo.component';
// import { RegularizationPermissionComponent } from '../attendance/regularization-permission/regularization-permission.component';
import { RegularizationPermissionComponent } from '../attendance/regularization-permission/regularization-permission.component'; 
@Component({
  selector: 'app-attendance-management',
  standalone: true,
    imports: [ RouterModule],
   templateUrl: './attendance-management.component.html',
  styleUrls: ['./attendance-management.component.css']
})
export class AttendanceManagementComponent {

}
