package com.example.User.repository;

import com.example.User.entity.Attendance;
import com.example.User.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    //created by hamad for attendence module

    Optional<Attendance> findByEmployeeAndAttendanceDate(Employee employee, LocalDate date);
    List<Attendance> findByEmployee_EmployeeId(Long employeeId);
    //added by for checkin
    void deleteByEmployeeEmployeeId(Long employeeId);
    List<Attendance> findByAttendanceDateBetween(LocalDate start, LocalDate end);
}
