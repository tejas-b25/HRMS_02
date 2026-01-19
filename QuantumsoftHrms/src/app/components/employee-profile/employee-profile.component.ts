import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

interface Employee {
  // Basic Information
  id: string;
  name: string;
  designation: string;
  qualification: string;
  department: string;
  email: string;
  phone: string;
  photoUrl?: string;
  
  // Profile Information
  location: string;
  bloodGroup: string;
  maritalStatus: string;
  dateOfBirth: string;
  religion: string;
  height: string;
  
  // Bank & Personal
  bankAccount: string;
  marriageDate: string;
  residentialStatus: string;
  physicallyChallenged: string;
  weight: string;
  
  // Corporate
  corporateEmail: string;
  extension: string;
  
  // Secondary Information
  nationality: string;
  spouse: string;
  alternatePhone: string;
  internationalEmployeeNo: string;
  identificationMark: string;
  
  // Address
  contactAddress: string;
  address: string;
  emergencyContactName: string;
  emergencyPhone1: string;
  emergencyExtension: string;
}

@Component({
  selector: 'app-employee-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './employee-profile.component.html',
  styleUrls: ['./employee-profile.component.css']
})
export class EmployeeProfileComponent implements AfterViewInit {
  employee: Employee = {
    // Basic Information
    id: 'qs12355',
    name: 'Priya Tavare',
    designation: 'Full Stack Developer',
    qualification: 'B.Tech CSE',
    department: 'Frontend',
    email: 'priyatavare@quantumsoficompany.com',
    phone: '+91-9876543210',
    photoUrl: '/assets/profile.jpeg',
    
    // Profile Information
    location: 'Pune, Maharashtra',
    bloodGroup: 'B+',
    maritalStatus: 'Single',
    dateOfBirth: '2002-03-31',
    religion: 'Hindu',
    height: '5\'3"',
    
    // Bank & Personal
    bankAccount: 'XX-XXX-XXXX',
    marriageDate: 'N/A',
    residentialStatus: 'Indian Resident',
    physicallyChallenged: 'No',
    weight: '52 kg',
    
    // Corporate
    corporateEmail: 'priya.t@company.com',
    extension: '1234',
    
    // Secondary Information
    nationality: 'Indian',
    spouse: 'N/A',
    alternatePhone: '+91-9876543211',
    internationalEmployeeNo: 'INT-789456',
    identificationMark: 'Mole on left cheek',
    
    // Address
    contactAddress: 'Pune, Maharashtra',
    address: '123 Main Street, Pune, Maharashtra - 560001',
    emergencyContactName: 'Priyanka Tavare',
    emergencyPhone1: '+91-9876543212',
    emergencyExtension: 'N/A'
  };

  // Track which sections are editable
  editableSections = {
    profile: false,
    personal: false,
    bank: false,
    corporate: false,
    secondary: false,
    address: false
  };

  // Active tab state
  activeTab: string = 'profile';

  // Temporary storage for edited values
  editedEmployee: Employee = { ...this.employee };

  // Navigation Methods - FIXED SCROLLING
  scrollToSection(sectionId: string) {
    this.activeTab = sectionId;
    this.updateActiveTab();
    
    const element = document.getElementById(sectionId);
    if (element) {
      // Calculate the position to scroll to
      const elementRect = element.getBoundingClientRect();
      const absoluteElementTop = elementRect.top + window.pageYOffset;
      const offset = 120; // Adjust this based on your header height
      
      window.scrollTo({
        top: absoluteElementTop - offset,
        behavior: 'smooth'
      });
    }
  }

  updateActiveTab() {
    // Remove active class from all tabs
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => tab.classList.remove('active'));
    
    // Add active class to current tab
    const tabElements = document.querySelectorAll('.tab');
    const tabOrder = ['profile', 'personal', 'bank', 'corporate', 'secondary', 'address'];
    const tabIndex = tabOrder.indexOf(this.activeTab);
    
    if (tabIndex >= 0 && tabElements[tabIndex]) {
      tabElements[tabIndex].classList.add('active');
    }
  }

  ngAfterViewInit() {
    this.setupScrollSpy();
  }

  setupScrollSpy() {
    const sections = document.querySelectorAll('.section-card');
    const options = {
      root: null,
      rootMargin: '-20% 0px -60% 0px', // Adjust these values to control when the section is considered "active"
      threshold: 0
    };

    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          this.activeTab = entry.target.id;
          this.updateActiveTab();
        }
      });
    }, options);

    sections.forEach(section => {
      observer.observe(section);
    });
  }

  // Edit Methods
  toggleEdit(section: string) {
    this.editableSections[section as keyof typeof this.editableSections] = 
      !this.editableSections[section as keyof typeof this.editableSections];
    
    if (this.editableSections[section as keyof typeof this.editableSections]) {
      this.editedEmployee = { ...this.employee };
    }
  }

  saveSection(section: string) {
    this.employee = { ...this.editedEmployee };
    this.editableSections[section as keyof typeof this.editableSections] = false;
    alert(`${section.charAt(0).toUpperCase() + section.slice(1)} information updated successfully!`);
  }

  cancelEdit(section: string) {
    this.editableSections[section as keyof typeof this.editableSections] = false;
    this.editedEmployee = { ...this.employee };
  }

  isAnySectionEditing(): boolean {
    return Object.values(this.editableSections).some(value => value);
  }
}