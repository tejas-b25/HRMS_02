import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
// import { FormsComponent } from '../../document-center/forms/forms.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-review',
  standalone: true,
  imports: [RouterModule,CommonModule,FormsModule],
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent {
   selectedTab: 'active' | 'closed' = 'active';
  searchText: string = '';

  filterEmployees() {
    // Implement your search logic here
    console.log('Searching for:', this.searchText);
  }
  }