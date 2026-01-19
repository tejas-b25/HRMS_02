import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';

interface LeaveRequest {
  id: number;
  employee: string;
  type: string;
  start: string;   // YYYY-MM-DD
  end: string;     // YYYY-MM-DD
  totalDays: number;
  status: string;
  reason: string;
}

@Component({
  selector: 'app-leave-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './leave-management.component.html',
  styleUrls: ['./leave-management.component.css']
})
export class LeaveManagementComponent implements OnInit {
  leaveRequests: LeaveRequest[] = [];
  nextId = 1;

  // UI state
  dateRangeError = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Optionally load existing leave requests from service
    this.leaveRequests = [];
  }

  back(): void {
    this.router.navigate(['/admin/admin-home']);
  }

  private markAllTouched(form: NgForm) {
    if (!form || !form.controls) return;
    Object.keys(form.controls).forEach(k => {
      try { form.controls[k].markAsTouched(); } catch (e) {}
    });
  }

  private findFirstInvalidDomControl(formId = 'leave-request-form'): HTMLElement | null {
    const root = document.getElementById(formId);
    if (!root) {
      return document.querySelector('.ng-invalid') as HTMLElement | null;
    }
    const invalid = root.querySelector('.ng-invalid') as HTMLElement | null;
    if (invalid) return invalid;
    // fallback: find first required empty control
    const controls = root.querySelectorAll<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>('input, select, textarea');
    for (const c of Array.from(controls)) {
      try {
        if ((c.required || c.hasAttribute('required')) && (c.value === '' || c.value == null)) return c as HTMLElement;
      } catch {}
    }
    return null;
  }

  saveRequest(form: NgForm): void {
    // mark touched so inline messages show
    this.markAllTouched(form);
    // reset date-range error flag
    this.dateRangeError = false;

    // basic form validity
    if (!form.valid) {
      const firstInvalid = this.findFirstInvalidDomControl();
      if (firstInvalid) setTimeout(() => firstInvalid.focus(), 50);
      return;
    }

    // additional cross-field validation: start <= end
    const v = form.value;
    const start = new Date(v.start);
    const end = new Date(v.end);
    if (isNaN(start.getTime()) || isNaN(end.getTime()) || end < start) {
      // set a flag so template shows the date-range error message under end date
      this.dateRangeError = true;
      const firstInvalid = this.findFirstInvalidDomControl();
      // focus end date if it's the problematic field
      const endEl = document.getElementById('endDate') as HTMLElement | null;
      if (endEl) setTimeout(() => endEl.focus(), 50);
      return;
    }

    const newRequest: LeaveRequest = {
      id: this.nextId++,
      employee: v.employeeName,
      type: v.type,
      start: v.start,
      end: v.end,
      totalDays: Number(v.totalDays),
      status: 'Pending',
      reason: v.reason
    };

    this.leaveRequests.push(newRequest);

    console.log('Saved leave request:', newRequest);
    form.resetForm();
  }

  viewRequest(r: LeaveRequest) {
    alert(`Request #${r.id}\n${r.employee}\n${r.type}\n${r.start} → ${r.end}\nReason: ${r.reason}\nStatus: ${r.status}`);
  }

  deleteRequest(id: number) {
    this.leaveRequests = this.leaveRequests.filter(r => r.id !== id);
  }
}
