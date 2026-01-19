import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RegularizationRequestPayload, RegularizationResponse, RegularizationService } from '../../../../../services/regularization-request.service';


@Component({
  selector: 'app-admin-regularization',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './regularization-request.component.html',
  styleUrls: ['./regularization-request.component.css']
})
export class RegularizationRequestComponent implements OnInit {

  requests: RegularizationResponse[] = [];

  // Admin ID (from login/JWT ideally)
  approverId = 3;

  form: RegularizationRequestPayload = {
    employeeId: 0,
    attendanceDate: '',
    requestedInTime: '',
    requestedOutTime: '',
    reason: ''
  };

  constructor(private regService: RegularizationService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.regService.getAll().subscribe(res => {
      this.requests = res;
    });
  }

  submit() {
    if (!this.form.employeeId) {
      alert('Employee ID is required');
      return;
    }

    this.regService.create(this.form).subscribe(() => {
      alert('Regularization created for employee');
      this.resetForm();
      this.loadAll();
    });
  }

  approve(r: RegularizationResponse) {
    this.regService
      .approve(r.attendanceRegId, this.approverId)
      .subscribe(updated => r.status = updated.status);
  }

  reject(r: RegularizationResponse) {
    this.regService
      .reject(r.attendanceRegId, this.approverId)
      .subscribe(updated => r.status = updated.status);
  }

  resetForm() {
    this.form = {
      employeeId: 0,
      attendanceDate: '',
      requestedInTime: '',
      requestedOutTime: '',
      reason: ''
    };
  }
}
