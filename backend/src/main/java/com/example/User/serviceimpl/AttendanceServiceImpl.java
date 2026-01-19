package com.example.User.serviceimpl;

import com.example.User.entity.Attendance;
import com.example.User.entity.AttendanceStatus;
import com.example.User.entity.DeviceType;
import com.example.User.entity.Employee;
import com.example.User.repository.AttendanceRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.service.AttendanceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Attendance clockIn(Long employeeId, String geoLocation, String deviceType) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        attendanceRepository.findByEmployeeAndAttendanceDate(employee, today)
                .ifPresent(a -> {
                    throw new RuntimeException("Already clocked in today");
                });

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(today)
                .clockInTime(LocalDateTime.now())
                .geoLocation(geoLocation)
                .deviceType(DeviceType.valueOf(deviceType.toUpperCase()))
                .status(AttendanceStatus.PRESENT)
                .build();

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance clockOut(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByEmployeeAndAttendanceDate(employee,
                        today)

                .orElseThrow(() -> new RuntimeException("No clock-in record found"));

        if (attendance.getClockOutTime() != null)
            throw new RuntimeException("Already clocked out");

        attendance.setClockOutTime(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceSummary(Long employeeId, LocalDate startDate,
                                                 LocalDate endDate) {

        List<Attendance> records = attendanceRepository.findByEmployee_EmployeeId(employeeId);
        return records.stream()
                .filter(a -> !a.getAttendanceDate().isBefore(startDate)
                        && !a.getAttendanceDate().isAfter(endDate))
                .toList();
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}
