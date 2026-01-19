package com.example.User.config;

import com.example.User.entity.Employee;
import com.example.User.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
// Created by hamad task2
@Component("employeeSecurity") // 👈 this name must match the one used in @PreAuthorize
@RequiredArgsConstructor
public class EmployeeSecurity {

    private final EmployeeRepository employeeRepository;

    // Check if manager is viewing a team member
    public boolean isTeamMember(Long employeeId, Authentication authentication) {
        String username = authentication.getName();

        // Find manager's employee record by username (via email or user link)
        // Assuming you have user-email linkage:
        Employee emp = employeeRepository.findById(employeeId).orElse(null);
        if (emp == null || emp.getReportingManager() == null) {
            return false;
        }

        // Compare manager's username/email with logged-in username
        String managerEmail = emp.getReportingManager().getEmail();
        return username.equalsIgnoreCase(managerEmail);
    }

    // Check if employee is viewing their own record
    public boolean isSelf(Long employeeId, Authentication authentication) {
        String username = authentication.getName();
        Employee emp = employeeRepository.findById(employeeId).orElse(null);
        if (emp == null) {
            return false;
        }
        return username.equalsIgnoreCase(emp.getEmail());
    }
}