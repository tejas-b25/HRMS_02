package com.example.User.service;

import com.example.User.entity.Attendance;
import com.example.User.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {  //created by hamad for attendence module

    Attendance clockIn( Long employeeId,String geoLocation, String deviceType);

    Attendance clockOut(Long employeeId);
    List<Attendance> getAttendanceSummary(Long employeeId, LocalDate startDate, LocalDate endDate);


    Employee getEmployeeByUsername(String username);
}
