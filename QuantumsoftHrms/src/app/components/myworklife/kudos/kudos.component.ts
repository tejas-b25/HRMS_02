import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-kudos',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './kudos.component.html',
  styleUrl: './kudos.component.css'
})
export class KudosComponent {
  tabs = ['Received', 'Given'];
  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  activeTab: string = 'received';


  setTab(tab: 'received' | 'given') {
    this.activeTab = tab;
  }
}