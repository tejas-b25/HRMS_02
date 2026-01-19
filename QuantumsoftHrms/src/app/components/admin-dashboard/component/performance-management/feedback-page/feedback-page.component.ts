import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  FeedbackPayload,
  FeedbackResponse,
  FeedbackThreeSixtyService
} from '../../../../../services/feedback-three-sixty.service';

@Component({
  selector: 'app-feedback-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './feedback-page.component.html',
  styleUrls: ['./feedback-page.component.css']
})
export class FeedbackPageComponent implements OnInit {

  reviewerId = 3;
  reviewerName = 'Manager';

  revieweeId!: number;
  revieweeName = '';

  comments = '';
  rating: number | null = null;

  feedbackList: FeedbackResponse[] = [];
  loading = false;

  constructor(private service: FeedbackThreeSixtyService) {}

  ngOnInit(): void {
    this.loadAllFeedback();
  }

  submitFeedback(form: any) {
    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }

    const payload: FeedbackPayload = {
      reviewerId: this.reviewerId,
      reviewerName: this.reviewerName,
      revieweeId: this.revieweeId,
      revieweeName: this.revieweeName,
      comments: this.comments,
      rating: this.rating!
    };

    this.service.createFeedback(payload).subscribe({
      next: () => {
        this.resetForm(form);
        this.loadAllFeedback();
        alert('Feedback submitted successfully');
      }
    });
  }

  loadAllFeedback() {
    this.loading = true;
    this.service.getAllFeedback().subscribe({
      next: res => {
        this.feedbackList = res;
        this.loading = false;
      }
    });
  }

  resetForm(form: any) {
    form.resetForm();
    this.rating = null;
  }
}
