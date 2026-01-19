import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgChartsModule } from 'ng2-charts';
import { ChartData, ChartOptions } from 'chart.js';

interface LeaveType {
  name: string;
  key: string;
  granted: number;
  consumed: number;
}

interface LeaveHistory {
  type: string;
  days: number;
  status: string;
  fromDate: string;
  toDate: string;
  reason: string;
}

@Component({
  selector: 'app-leave-balance',
  standalone: true,
  imports: [CommonModule, NgChartsModule],
  templateUrl: './leave-balance.component.html',
  styleUrls: ['./leave-balance.component.css'],
})
export class LeaveBalanceComponent implements OnInit {
  leaveTypes: LeaveType[] = [
    { name: 'Paid Leaves', key: 'paid', granted: 12, consumed: 0 },
    { name: 'Casual Leaves', key: 'casual', granted: 8, consumed: 0 },
    { name: 'Sick Leaves', key: 'sick', granted: 6, consumed: 0 },
  ];

  leaveHistory: LeaveHistory[] = [];
  showDetails = false;
  selectedLeaveType: string | null = null;
  detailsData: any = {};

  chartData: ChartData<'doughnut'> = { labels: [], datasets: [] };
  chartOptions: ChartOptions<'doughnut'> = {
    responsive: true,
    plugins: { legend: { position: 'bottom' } },
  };
  router: any;

  ngOnInit() {
    this.loadLeaveHistory();
    this.calculateConsumedLeaves();

    setInterval(() => {
      this.loadLeaveHistory();
      this.calculateConsumedLeaves();
    }, 1000);

    window.addEventListener('storage', () => {
      this.loadLeaveHistory();
      this.calculateConsumedLeaves();
    });
  }

  loadLeaveHistory() {
    const storedHistory = localStorage.getItem('leaveHistory');
    this.leaveHistory = storedHistory ? JSON.parse(storedHistory) : [];
  }

  calculateConsumedLeaves() {
    // Reset
    this.leaveTypes.forEach(l => (l.consumed = 0));

    for (const leave of this.leaveHistory) {
      if (leave.status === 'APPROVED') {
        const key = leave.type.toLowerCase().includes('paid')
          ? 'paid'
          : leave.type.toLowerCase().includes('casual')
          ? 'casual'
          : 'sick';
        const leaveType = this.leaveTypes.find(l => l.key === key);
        if (leaveType) leaveType.consumed += leave.days;
      }
    }
  }

  getBalance(leave: LeaveType): number {
    return leave.granted - leave.consumed;
  }

  getProgress(leave: LeaveType): number {
    return (leave.consumed / leave.granted) * 100;
  }

  applyLeave() {
    alert('Redirecting to Leave Apply page...');
    this.router.navigate(['apply-leave']);
    
  }

  downloadReport() {
    alert('Downloading leave balance report...');
  }

  viewDetails(type: string) {
    this.selectedLeaveType = type;
    this.showDetails = true;

    const leaveType = this.leaveTypes.find(l => l.key === type);
    const filteredHistory = this.leaveHistory.filter(h => {
      const key = h.type.toLowerCase().includes('paid')
        ? 'paid'
        : h.type.toLowerCase().includes('casual')
        ? 'casual'
        : 'sick';
      return key === type && h.status === 'APPROVED';
    });

    this.detailsData = {
      type: leaveType?.name || type,
      granted: leaveType?.granted || 0,
      consumed: leaveType?.consumed || 0,
      available: (leaveType?.granted || 0) - (leaveType?.consumed || 0),
      history: filteredHistory,
    };

    this.updateChart();
  }

  updateChart() {
  this.chartData = {
    labels: ['Consumed', 'Available'],
    datasets: [
      {
        data: [this.detailsData.consumed, this.detailsData.available],
        backgroundColor: ['#f87171', '#34d399'],
        spacing: 5,     
      },
    ],
  };

  this.chartOptions = {
    responsive: true,
    cutout: '70%',     
    plugins: { legend: { position: 'bottom' } },
  };
}



  goBack() {
    this.showDetails = false;
    this.selectedLeaveType = null;
  }
}
