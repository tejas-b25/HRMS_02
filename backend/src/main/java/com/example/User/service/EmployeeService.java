package com.example.User.service;

import com.example.User.dto.EmployeeResponseDTO;
import com.example.User.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface EmployeeService {  // created by hamad task2

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Long employeeId, Employee employee);

    void deleteEmployee(Long employeeId);

    Optional<Employee> getEmployeeById(Long employeeId);

   // List<Employee> getAllEmployees();

    List<Employee> searchEmployees(String name, String email, String department, String designation);

    List<Employee> filterEmployees(String status, String department, String employmentType);

    Employee findByEmail(String email);

    List<Employee> getTeamMembers(Long managerId);

    //List<Employee> getEmployeesUnderManager(Long managerId);

    //added

//    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
//    List<Employee> getEmployeesForUser(Long userId);


    List<Employee> getEmployeesForManager(Long managerId);
    List<EmployeeResponseDTO> getAllEmployees(); // For HR or Admin

}
