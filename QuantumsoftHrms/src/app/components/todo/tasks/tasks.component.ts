import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent {
  showForm = false;

  
  tasks: any[] = [];

  newTask = {
    title: '',
    description: '',
    dueDate: '',
    priority: 'Normal',
    status: 'Pending'
  };

  toggleForm() {
    this.showForm = !this.showForm;
  }

 
  addTask() {
    if (this.newTask.title && this.newTask.dueDate) {
      this.tasks.push({ ...this.newTask }); 
      this.newTask = {
        title: '',
        description: '',
        dueDate: '',
        priority: 'Normal',
        status: 'Pending'
      };
      this.showForm = false;
    } else {
      alert('Please fill in at least task title and due date.');
    }
  }

  deleteTask(index: number) {
    this.tasks.splice(index, 1);
  }
}