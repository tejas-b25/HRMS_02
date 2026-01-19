import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PerformanceReviewPayload, PerformanceReviewService } from '../../../../../services/performance-review.service';

@Component({
  selector: 'app-performance-review',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './performance-review.component.html',
  styleUrls: ['./performance-review.component.css']
})
export class PerformanceReviewComponent implements OnInit {

  reviews: PerformanceReviewPayload[] = [];

  isEditMode = false;
  selectedReviewId: number | null = null;

  form: PerformanceReviewPayload = {
    employeeId: 0,
    kpiId: 0,
    reviewPeriod: '',
    selfScore: undefined,
    managerScore: undefined,
    feedback: '',
    reviewStatus: 'Pending'
  };

  constructor(private service: PerformanceReviewService) {}

  ngOnInit(): void {
    this.loadEmployeeReviews(8);
  }

  submit(f: any) {
    if (f.invalid) {
      f.control.markAllAsTouched();
      return;
    }

    if (this.isEditMode && this.selectedReviewId) {
      this.service.updateReview(this.selectedReviewId, this.form)
        .subscribe(() => {
          this.reset(f);
          this.loadEmployeeReviews(this.form.employeeId);
        });
    } else {
      this.service.createReview(this.form)
        .subscribe(() => {
          this.reset(f);
          this.loadEmployeeReviews(this.form.employeeId);
        });
    }
  }

  edit(review: PerformanceReviewPayload) {
    this.isEditMode = true;
    this.selectedReviewId = review.reviewId!;
    this.form = { ...review };
  }

  delete(reviewId: number, employeeId: number) {
    if (!confirm('Delete this review?')) return;
    this.service.deleteReview(reviewId)
      .subscribe(() => this.loadEmployeeReviews(employeeId));
  }

  loadEmployeeReviews(employeeId: number) {
    this.service.getReviewsByEmployee(employeeId)
      .subscribe(res => this.reviews = res);
  }

  reset(f?: any) {
    this.isEditMode = false;
    this.selectedReviewId = null;
    this.form = {
      employeeId: 0,
      kpiId: 0,
      reviewPeriod: '',
      selfScore: undefined,
      managerScore: undefined,
      feedback: '',
      reviewStatus: 'Pending'
    };
    f?.resetForm();
  }
}
