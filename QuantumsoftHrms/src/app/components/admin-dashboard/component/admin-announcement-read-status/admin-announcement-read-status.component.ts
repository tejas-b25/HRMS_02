// import { Component, OnInit } from '@angular/core';
// import { ActivatedRoute } from '@angular/router';
// import { AnnouncementService } from '../../../../services/announcement.service';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-admin-announcement-read-status',
//   standalone: true,
//   imports: [CommonModule],
//   templateUrl: './admin-announcement-read-status.component.html'
// })
// export class AdminAnnouncementReadStatusComponent implements OnInit {

//   announcementId!: number;
//   statuses: any[] = [];
//   loading = true;

//   constructor(
//     private route: ActivatedRoute,
//     private service: AnnouncementService
//   ) {}

//   ngOnInit(): void {
//     this.announcementId = Number(this.route.snapshot.paramMap.get('id'));
//     this.service.getAnnouncementReadStatus(this.announcementId)
//       .subscribe(res => {
//         this.statuses = res;
//         this.loading = false;
//       });
//   }
// }
