package com.example.User.controller;

import com.example.User.entity.Attendance;
import com.example.User.entity.Employee;
import com.example.User.entity.User;
import com.example.User.repository.UserRepository;
import com.example.User.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceController {  //created by hamad for attendence module

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserRepository userRepository;

    // ----------Employee Clock-In---------------
    @PostMapping("/clock-in")
    @PreAuthorize("hasRole('EMPLOYEE') OR hasRole('ADMIN') OR hasRole('HR') OR hasRole('MANAGER')")
    public ResponseEntity<Attendance> clockIn(@RequestBody Map<String, String> request) {
        //new changes for emoloyee also available in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // this is the logged-in username
        User loggedInUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
        // fetch employee using username
        Employee employee = attendanceService.getEmployeeByUsername(username);
        //----
        Long employeeId = employee.getId();
        String geoLocation = request.getOrDefault("geoLocation", null);
        String deviceType = request.getOrDefault("deviceType", "WEB");
        return ResponseEntity.ok(attendanceService.clockIn(employeeId, geoLocation, deviceType));
    }
    //------- Employee Clock-Out--------------

    @PostMapping("/clock-out")
    @PreAuthorize("hasRole('EMPLOYEE') OR hasRole('ADMIN') OR hasRole('HR') OR hasRole('MANAGER')")
    public ResponseEntity<Attendance> clockOut() {
        // Get logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User loggedInUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        // Fetch Employee linked to the logged-in user
        Employee employee = attendanceService.getEmployeeByUsername(username);

        // Clock-out using employee's ID
        Attendance attendance = attendanceService.clockOut(employee.getId());
        return ResponseEntity.ok(attendance);
    }

    // ---------Attendance Summary Employee/HR/Admin--------
    @GetMapping("/{employeeId}/summary")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','HR','ADMIN')")
    public ResponseEntity<List<Attendance>> getAttendanceSummary(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(attendanceService.getAttendanceSummary(employeeId, startDate, endDate));
    }


}
