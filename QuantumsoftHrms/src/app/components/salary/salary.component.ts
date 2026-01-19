import { Component } from '@angular/core';
import { RouterOutlet } from "@angular/router";
@Component({
  selector: 'app-salary',
  standalone: true,             
  imports: [RouterOutlet],
  templateUrl: './salary.component.html',
  styleUrl: './salary.component.css'
})    
export class SalaryComponent {
}