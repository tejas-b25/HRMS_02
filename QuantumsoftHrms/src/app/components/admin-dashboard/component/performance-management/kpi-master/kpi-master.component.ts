import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { KpiMasterService, KpiPayload } from '../../../../../services/kpi-master.service';

@Component({
  selector: 'app-kpi-master',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './kpi-master.component.html',
  styleUrls: ['./kpi-master.component.css']
})
export class KpiMasterComponent implements OnInit {

  kpis: KpiPayload[] = [];
  isEditMode = false;
  selectedId!: number;

  form: KpiPayload = {
    departmentId: 0,
    role: 'EMPLOYEE',
    kpiName: '',
    kpiDescription: '',
    weightage: 0
  };

  constructor(private service: KpiMasterService) {}

  ngOnInit(): void {
    this.loadKpis();
  }

  submit(f: any) {
    if (f.invalid) {
      f.control.markAllAsTouched();
      return;
    }

    if (this.isEditMode) {
      this.service.updateKpi(this.selectedId, this.form).subscribe(() => {
        this.reset(f);
        this.loadKpis();
      });
    } else {
      this.service.createKpi(this.form).subscribe(() => {
        this.reset(f);
        this.loadKpis();
      });
    }
  }

  edit(kpi: KpiPayload) {
    this.isEditMode = true;
    this.selectedId = kpi.kpiId!;
    this.form = { ...kpi };
  }

  delete(id: number) {
    if (confirm('Delete this KPI?')) {
      this.service.deleteKpi(id).subscribe(() => this.loadKpis());
    }
  }

  reset(f?: any) {
    this.isEditMode = false;
    this.form = {
      departmentId: 0,
      role: 'EMPLOYEE',
      kpiName: '',
      kpiDescription: '',
      weightage: 0
    };
    f?.resetForm();
  }

  loadKpis() {
    this.service.getAllKpis().subscribe(res => this.kpis = res);
  }
}
