package com.example.User.controller;

import com.example.User.dto.EmployeeResponseDTO;
import com.example.User.entity.Employee;
import com.example.User.repository.EmployeeRepository;
import com.example.User.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    // ----------------- Create Employee -----------------
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee created = employeeService.createEmployee(employee);
        return ResponseEntity.ok(created);
    }

    // ----------------- Update Employee -----------------
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updated = employeeService.updateEmployee(id, employee);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ----------------- Delete Employee -----------------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    // ----------------- View Employee by ID -----------------

    @GetMapping("/{id}")
     @PreAuthorize("hasRole('ADMIN')  or hasRole('HR') ")
     public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
       return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


//    // ----------------- View All Employees -----------------

    // HR/Admin: Fetch all employees
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
    return ResponseEntity.ok(employeeService.getAllEmployees());
    }


    // ----------------- Search Employees -----------------
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String designation
    ) {
        return ResponseEntity.ok(employeeService.searchEmployees
                (name, email, department, designation));
    }

    // ----------------- Filter Employees -----------------
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<List<Employee>> filterEmployees(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String employmentType
    ) {
        return ResponseEntity.ok(employeeService.filterEmployees(status, department, employmentType));
    }


    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<Employee>> getEmployeesForManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.getEmployeesForManager(managerId));
    }

}