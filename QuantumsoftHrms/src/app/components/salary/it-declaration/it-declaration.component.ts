import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-it-declaration',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './it-declaration.component.html',
  styleUrl: './it-declaration.component.css'
})
export class ItDeclarationComponent {
selectedTab: any;
  // backgroundImageUrl = 'assets/images/background.png';
selectTab(arg0: string) {
throw new Error('Method not implemented.');
}

}