import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  PerformanceDashboard,
  PerformanceDashboardService
} from '../../../../../services/performance-dashboard.service';

@Component({
  selector: 'app-performance-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './performance-dashboard.component.html',
  styleUrls: ['./performance-dashboard.component.css']
})
export class PerformanceDashboardComponent implements OnInit {

  dashboards: PerformanceDashboard[] = [];

  filterDepartment = '';
  filterPeriod = '';

  form: PerformanceDashboard = {
    departmentName: '',
    avgScore: 0,
    totalEmployees: 0,
    topScore: 0,
    bottomScore: 0,
    reviewPeriod: ''
  };

  constructor(private service: PerformanceDashboardService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.service.getAllDashboards()
      .subscribe(res => this.dashboards = res);
  }

  submit(f: any) {
    if (f.invalid) {
      f.control.markAllAsTouched();
      return;
    }

    this.service.createDashboard(this.form).subscribe(() => {
      this.reset(f);
      this.loadAll();
    });
  }

  filterByDepartment() {
    if (!this.filterDepartment) {
      this.loadAll();
      return;
    }
    this.service.getByDepartment(this.filterDepartment)
      .subscribe(res => this.dashboards = res);
  }

  filterByPeriod() {
    if (!this.filterPeriod) {
      this.loadAll();
      return;
    }
    this.service.getByPeriod(this.filterPeriod)
      .subscribe(res => this.dashboards = res);
  }

  delete(id: number) {
    if (!confirm('Delete dashboard summary?')) return;
    this.service.deleteDashboard(id).subscribe(() => this.loadAll());
  }

  reset(f?: any) {
    this.form = {
      departmentName: '',
      avgScore: 0,
      totalEmployees: 0,
      topScore: 0,
      bottomScore: 0,
      reviewPeriod: ''
    };
    f?.resetForm();
  }
}
