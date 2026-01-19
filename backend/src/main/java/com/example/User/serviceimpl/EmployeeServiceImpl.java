package com.example.User.serviceimpl;


import com.example.User.dto.EmployeeResponseDTO;
import com.example.User.entity.*;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.AttendanceRepository;
import com.example.User.repository.DocumentRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.UserRepository;
import com.example.User.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//created by hamad task2
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private DocumentRepository documentRepository;


@Override
public Employee createEmployee(Employee employee) {
    if (employee.getPassword() != null) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
    }

    // Create corresponding User record
    User user = new User();
    user.setUsername(employee.getUsername());
    user.setPassword(employee.getPassword());
    user.setEmail(employee.getEmail());
    user.setFirstName(employee.getFirstName());
    user.setLastName(employee.getLastName());
    user.setRole(employee.getRole()); // Enum
    user.setStatus("ACTIVE");

    // Save User first
    userRepository.save(user);

    // Link this user to employee
    employee.setUser(user);

    if (employee.getReportingManager() != null && employee.getReportingManager().getId() != null) {
        User manager = userRepository.findById(employee.getReportingManager().getId())
                .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + employee.getReportingManager().getId()));
        employee.setReportingManager(manager);
    }

    if (employee.getHrbp() != null && employee.getHrbp().getId() != null) {
        User hrbp = userRepository.findById(employee.getHrbp().getId())
                .orElseThrow(() -> new RuntimeException("HRBP not found with ID: " + employee.getHrbp().getId()));
        employee.setHrbp(hrbp);
    }

    return employeeRepository.save(employee);
}

@Transactional
@Override
public Employee updateEmployee(Long employeeId, Employee employee) {
    Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

    if (optionalEmployee.isPresent()) {
        Employee existingEmployee = optionalEmployee.get();

        // ----- Update simple fields -----
        existingEmployee.setUsername(employee.getUsername());
        existingEmployee.setPassword(employee.getPassword());
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setDateOfBirth(employee.getDateOfBirth());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setContactNumber(employee.getContactNumber());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setBloodGroup(employee.getBloodGroup());
        existingEmployee.setDateOfJoining(employee.getDateOfJoining());
        existingEmployee.setDesignation(employee.getDesignation());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setEmploymentType(employee.getEmploymentType());
        existingEmployee.setStatus(employee.getStatus());
        existingEmployee.setRole(employee.getRole());
        existingEmployee.setReportingManager(employee.getReportingManager());
        existingEmployee.setHrbp(employee.getHrbp());
        existingEmployee.setUser(employee.getUser());

        // ----- Update child collection: attendances -----
        if (employee.getAttendances() != null) {
            existingEmployee.getAttendances().clear(); // clear old
            for (Attendance att : employee.getAttendances()) {
                att.setEmployee(existingEmployee); // maintain bidirectional
                existingEmployee.getAttendances().add(att);
            }
        }

        // ----- Update child collection: documents -----
        if (employee.getDocuments() != null) {
            existingEmployee.getDocuments().clear(); // clear old
            for (Document doc : employee.getDocuments()) {
                doc.setEmployee(existingEmployee);
                existingEmployee.getDocuments().add(doc);
            }
        }

        return employeeRepository.save(existingEmployee);
    }

    // Not found - throw custom exception or return null
    return null;
}

@Transactional
@Override
public void deleteEmployee(Long employeeId) {
    if (!employeeRepository.existsById(employeeId)) {
        throw new RuntimeException("Employee not found");
    }

    // delete child records first
    attendanceRepository.deleteByEmployeeEmployeeId(employeeId);
    documentRepository.deleteByEmployeeEmployeeId(employeeId);

    // then delete employee
    employeeRepository.deleteById(employeeId);
}




    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

//    @Override
//    public List<Employee> getAllEmployees() {
//        return employeeRepository.findAll();
//    }
@Override
public List<EmployeeResponseDTO> getAllEmployees() {
    return employeeRepository.findAll()
            .stream()
            .map(this::toEmployeeDTO)
            .collect(Collectors.toList());
}

    @Override
    public List<Employee> getTeamMembers(Long managerId) {
        return employeeRepository.findByReportingManagerId(managerId);
    }


    @Override
    public List<Employee> searchEmployees(String name, String email, String department, String designation) {
        List<Employee> result = employeeRepository.findAll();

        if (name != null && !name.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getFirstName().contains(name) || e.getLastName().contains(name))
                    .collect(Collectors.toList());
        }
        if (email != null && !email.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getEmail().contains(email))
                    .collect(Collectors.toList());
        }
        if (department != null && !department.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                    .collect(Collectors.toList());
        }
        if (designation != null && !designation.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getDesignation().equalsIgnoreCase(designation))
                    .collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public List<Employee> filterEmployees(String status, String department, String employmentType) {
        List<Employee> result = employeeRepository.findAll();

        if (status != null && !status.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }
        if (department != null && !department.isEmpty()) {
            result = result.stream()
                    .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                    .collect(Collectors.toList());
        }

        if (employmentType != null && !employmentType.isEmpty()) {
            EmploymentType typeEnum = EmploymentType.valueOf(employmentType.toUpperCase());

            result = result.stream()
                    .filter(e -> e.getEmploymentType() == typeEnum)
                    .collect(Collectors.toList());
        }
        return result;
    }
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

//
//    @Override
//    public List<Employee> getEmployeesUnderManager(Long managerId) {
//        return employeeRepository.findByManagerId(managerId);
//    }

    //added

    @Override
    public List<Employee> getEmployeesForManager(Long managerId) {
        List<Employee> employees = employeeRepository.findByReportingManager_Id(managerId);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found under manager with ID: " + managerId);
        }
        return employees;
    }

//    @Override
//    public List<Employee> getAllEmployees() {
//        return null;
//    }


    private EmployeeResponseDTO toEmployeeDTO(Employee emp) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setEmployeeId(emp.getEmployeeId());
        dto.setUsername(emp.getUsername());
        dto.setFirstName(emp.getFirstName());
        dto.setLastName(emp.getLastName());
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        dto.setContactNumber(emp.getContactNumber());

        dto.setDesignation(emp.getDesignation());
        dto.setDepartment(emp.getDepartment());
        dto.setEmploymentType(emp.getEmploymentType().name());
        dto.setStatus(emp.getStatus());

        if (emp.getReportingManager() != null) {
            dto.setReportingManagerId(emp.getReportingManager().getId());
            //dto.setReportingManagerName(String.valueOf(emp.getReportingManager().getId()));
        }

        if (emp.getHrbp() != null) {
            dto.setHrbpId(emp.getHrbp().getId());

        }

        return dto;
    }
}

