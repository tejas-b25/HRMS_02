import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ShiftService } from '../../../../../services/shift.service';

@Component({
  selector: 'app-shift-master',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './shift-master.component.html',
  styleUrls: ['./shift-master.component.css']
})
export class ShiftMasterComponent implements OnInit {

  shifts: any[] = [];
  isEdit = false;
  editId!: number;

  shiftForm = {
    shiftName: '',
    startTime: '',
    endTime: '',
    description: ''
  };

  constructor(private shiftService: ShiftService) {}

  ngOnInit(): void {
    this.loadShifts();
  }

  loadShifts() {
    this.shiftService.getAllShifts().subscribe(res => this.shifts = res);
  }

  submit() {
    if (this.isEdit) {
      this.shiftService.updateShift(this.editId, this.shiftForm).subscribe(() => {
        this.reset();
        this.loadShifts();
      });
    } else {
      this.shiftService.createShift(this.shiftForm).subscribe(() => {
        this.reset();
        this.loadShifts();
      });
    }
  }

  editShift(shift: any) {
    this.isEdit = true;
    this.editId = shift.shiftId;
    this.shiftForm = { ...shift };
  }

  deleteShift(id: number) {
    if (confirm('Delete this shift?')) {
      this.shiftService.deleteShift(id).subscribe(() => this.loadShifts());
    }
  }

  reset() {
    this.isEdit = false;
    this.shiftForm = { shiftName: '', startTime: '', endTime: '', description: '' };
  }
}