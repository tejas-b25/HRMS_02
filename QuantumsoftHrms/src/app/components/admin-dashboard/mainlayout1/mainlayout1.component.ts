import { Component } from '@angular/core';
import { AdminDashboardComponent } from "../admin-dashboard.component";
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-mainlayout1',
  standalone: true,
  imports: [AdminDashboardComponent,CommonModule,RouterOutlet],
  templateUrl: './mainlayout1.component.html',
  styleUrl: './mainlayout1.component.css'
})
export class Mainlayout1Component {

}
