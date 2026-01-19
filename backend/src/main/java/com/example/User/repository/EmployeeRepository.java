package com.example.User.repository;

import com.example.User.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// Created by hamad for task2
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Search employees by first name or last name (partial match)
    @Query(value = "SELECT * FROM employee e WHERE e.first_name LIKE %:name% OR e.last_name LIKE %:name%", nativeQuery = true)
    List<Employee> searchByName(@Param("name") String name);

    @Query(value = "SELECT * FROM employee e WHERE e.email LIKE %:email%", nativeQuery = true)
    List<Employee> searchByEmail(@Param("email") String email);

    // Filter employees by department
    @Query(value = "SELECT * FROM employee e WHERE e.department = :department", nativeQuery = true)
    List<Employee> filterByDepartment(@Param("department") String department);

    // Filter employees by designation
    @Query(value = "SELECT * FROM employee e WHERE e.designation = :designation", nativeQuery = true)
    List<Employee> filterByDesignation(@Param("designation") String designation);

    // Filter employees by status
    @Query(value = "SELECT * FROM employee e WHERE e.status = :status", nativeQuery = true)
    List<Employee> filterByStatus(@Param("status") String status);

    // Filter employees by employment type
    @Query(value = "SELECT * FROM employee e WHERE e.employment_type = :employmentType", nativeQuery = true)
    List<Employee> filterByEmploymentType(@Param("employmentType") String employmentType);

    @Query(value = "SELECT * FROM employees WHERE reporting_manager_id = :managerId", nativeQuery = true)
    List<Employee> findByReportingManagerId(@Param("managerId") Long managerId);

    @Query(value = "SELECT * FROM employees WHERE email = :email", nativeQuery = true)
    Employee findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM employees WHERE reporting_manager_id = :managerId", nativeQuery = true)
    List<Employee> findByManagerId(Long managerId);

    //List<Employee> findByReportingManager_Id(Long managerId);
    Optional<Employee> findByUserUsername(String username);

    List<Employee> findByReportingManager_Id(Long managerId);
    Optional<Employee> findByUser_Id(Long userId);

    Optional<Employee> findByUsername(String username);

   // List<Employee> findByReportingManager_Id(Long managerId);
}
