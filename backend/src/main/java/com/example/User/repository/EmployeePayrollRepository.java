package com.example.User.repository;

import com.example.User.entity.EmployeePayroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeePayrollRepository extends JpaRepository<EmployeePayroll, Long> {
    Optional<EmployeePayroll> findByEmployeeIdAndMonth(Long employeeId, String month);
}
