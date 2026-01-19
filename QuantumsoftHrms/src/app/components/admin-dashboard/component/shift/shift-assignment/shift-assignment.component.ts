import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ShiftService } from '../../../../../services/shift.service';

@Component({
  selector: 'app-shift-assignment',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './shift-assignment.component.html',
  styleUrls: ['./shift-assignment.component.css']
})
export class ShiftAssignmentComponent {

  assignment = {
    employeeId: '',
    shiftId: '',
    effectiveFromDate: '',
    effectiveToDate: ''
  };

  assignments: any[] = [];   

  result: any;

  constructor(private shiftService: ShiftService) {}


  assign() {
    this.shiftService.assignShift(this.assignment).subscribe(res => {
      // convert single object to array
      this.assignments = [res];
      alert('Shift Assigned Successfully');
    });
  }

  loadByEmployee() {
    this.shiftService
      .getShiftByEmployee(+this.assignment.employeeId)
      .subscribe(res => {
        // handle both object & array response
        this.assignments = Array.isArray(res) ? res : [res];
      });
  }
}