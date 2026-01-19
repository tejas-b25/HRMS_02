import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface Employee {
  id: string;
  name: string;
  designation: string;
  location: string;
  joiningDate: string;
  dob: string;
  isStarred?: boolean;
}

@Component({
  selector: 'app-people',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './people.component.html',
  styleUrl: './people.component.css'
})
export class PeopleComponent implements OnInit{

  employees: Employee[] = [];
  filteredEmployees: Employee[] = [];
  selectedEmployee: Employee | null = null;

  activeTab: 'starred' | 'everyone' = 'starred';
  searchText: string = '';
  showAddForm: boolean = false;
  showResults: boolean = false;

  newEmployee: Employee = {
    id: '',
    name: '',
    designation: '',
    location: '',
    joiningDate: '',
    dob: '',
    isStarred: false
  };

  ngOnInit() {
    this.loadEmployees();
  }

  loadEmployees() {
    const stored = localStorage.getItem('employees');
    this.employees = stored ? JSON.parse(stored) : [];
  }

  saveEmployees() {
    localStorage.setItem('employees', JSON.stringify(this.employees));
  }

  toggleAddForm() {
    this.showAddForm = !this.showAddForm;
  }

  addEmployee() {
    if (
      this.newEmployee.name.trim() &&
      this.newEmployee.id.trim() &&
      this.newEmployee.designation.trim()
    ) {
      this.employees.push({ ...this.newEmployee });
      this.saveEmployees();
      this.newEmployee = {
        id: '',
        name: '',
        designation: '',
        location: '',
        joiningDate: '',
        dob: '',
        isStarred: false
      };
      this.showAddForm = false;
      alert('Employee added successfully!');
    } else {
      alert('Please fill all required fields!');
    }
  }

  searchEmployee() {
    const search = this.searchText.toLowerCase().trim();

    if (search.length === 0) {
      this.filteredEmployees = [];
      this.showResults = false;
      return;
    }

    const baseList =
      this.activeTab === 'starred'
        ? this.employees.filter(emp => emp.isStarred)
        : this.employees;

    this.filteredEmployees = baseList.filter(emp =>
      emp.name.toLowerCase().includes(search) ||
      emp.id.toLowerCase().includes(search)
    );

    this.showResults = true;
  }

  selectEmployee(emp: Employee) {
    this.selectedEmployee = emp;
  }

  switchTab(tab: 'starred' | 'everyone') {
    this.activeTab = tab;
    this.selectedEmployee = null;
    this.searchText = '';
    this.filteredEmployees = [];
    this.showResults = false;

    if (tab === 'starred') {
      this.filteredEmployees = this.employees.filter(e => e.isStarred);
      this.showResults = true;
    }
  }
}