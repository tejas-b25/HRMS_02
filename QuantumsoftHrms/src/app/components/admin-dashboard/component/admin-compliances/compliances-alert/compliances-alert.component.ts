import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ComplianceAlertService } from '../../../../../services/compliance-alert.service';

@Component({
  selector: 'app-compliance-alert',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './compliances-alert.component.html',
  styleUrls: ['./compliances-alert.component.css']
})
export class CompliancesAlertComponent implements OnInit {

  alerts: any[] = [];
  upcomingAlerts: any[] = [];
  historyAlerts: any[] = [];

  days = 20;
  isLoading = false;
  showForm = false;
  isEditMode = false;

  filters = {
    status: '',
    channel: '',
    fromDate: '',
    toDate: ''
  };

  alertForm: any = this.resetForm();

  constructor(private alertService: ComplianceAlertService) {}

  ngOnInit(): void {
    this.loadAllAlerts();
    this.loadUpcomingAlerts();
  }

  resetForm() {
    return {
      alertId: null,
      complianceId: null,
      complianceMappingId: null,
        alertType: 'RENEWAL',
      alertDate: '',
      triggerDateTime: '',
      channel: 'EMAIL',
      recipients: '',
      messageTemplateId: '',
      messagePayload: '',
      createdBy: '',
      remarks: ''
    };
  }

  /* ---------------- LOAD DATA ---------------- */
  loadAllAlerts() {
    this.isLoading = true;
    this.alertService.getAllAlerts().subscribe(res => {
      this.alerts = res;
      this.isLoading = false;
    });
  }

  loadUpcomingAlerts() {
    this.alertService.getUpcomingAlerts(this.days).subscribe(res => {
      this.upcomingAlerts = res;
    });
  }

  loadHistory() {
    this.alertService.getAlertHistory(this.filters).subscribe(res => {
      this.historyAlerts = res;
    });
  }

  /* ---------------- FORM ACTIONS ---------------- */
  openCreate() {
    this.isEditMode = false;
    this.alertForm = this.resetForm();
    this.showForm = true;
  }

  openEdit(alert: any) {
    this.isEditMode = true;
    this.alertForm = { ...alert };
    this.showForm = true;
  }

  cancelForm() {
    this.showForm = false;
    this.alertForm = this.resetForm();
  }

  saveAlert() {
    const payload = {
      complianceId: this.alertForm.complianceId,
      complianceMappingId: this.alertForm.complianceMappingId,
      alertType: this.alertForm.alertType,
      alertDate: this.alertForm.alertDate,
      triggerDateTime: this.alertForm.triggerDateTime,
      channel: this.alertForm.channel,
      recipients: this.alertForm.recipients,
      messageTemplateId: this.alertForm.messageTemplateId,
      messagePayload: this.alertForm.messagePayload,
      createdBy: this.alertForm.createdBy,
      remarks: this.alertForm.remarks
    };

    if (this.isEditMode) {
      this.alertService.updateAlert(this.alertForm.alertId, payload).subscribe(() => {
        alert('Compliance alert updated');
        this.afterSave();
      });
    } else {
      this.alertService.createAlert(payload).subscribe(() => {
        alert('Compliance alert created');
        this.afterSave();
      });
    }
  }

  afterSave() {
    this.cancelForm();
    this.loadAllAlerts();
    this.loadUpcomingAlerts();
  }

  deleteAlert(id: number) {
    if (confirm('Cancel this alert?')) {
      this.alertService.deleteAlert(id).subscribe(() => {
        this.loadAllAlerts();
        this.loadUpcomingAlerts();
      });
    }
  }
}
