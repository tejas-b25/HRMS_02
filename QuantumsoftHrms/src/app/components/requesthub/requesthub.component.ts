import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-requesthub',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './requesthub.component.html',
  styleUrls: ['./requesthub.component.css']
})
export class RequesthubComponent1 {
  activeTab: string = 'apply';
  selectedFile: File | null = null;

  setActiveTab(tab: string) {
    this.activeTab = tab;
    this.selectedFile = null; // reset file when switching tab
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }
}