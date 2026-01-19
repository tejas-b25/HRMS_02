package com.example.User.controller;

import com.example.User.dto.TimesheetRequestDTO;
import com.example.User.entity.Timesheet;
import com.example.User.exception.TimesheetException;
import com.example.User.repository.UserRepository;
import com.example.User.service.TimesheetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timesheets")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TimesheetController {

    @Autowired
    private TimesheetService timesheetService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/submit")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('HR')")
    public ResponseEntity<?> submitTimesheet(@Valid @RequestBody TimesheetRequestDTO dto) {
        try {
            Timesheet savedTimesheet = timesheetService.submitTimesheet(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTimesheet);
        } catch (TimesheetException e) {
            // Custom exception handling for validation errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Generic error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting timesheet");
        }
    }


    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE') or  hasRole('HR')")
    public ResponseEntity<List<Timesheet>> listTimesheetsForEmployee(@PathVariable Long employeeId) {
        List<Timesheet> timesheets = timesheetService.listTimesheetsForEmployee(employeeId);
        return ResponseEntity.ok(timesheets);
    }


    @PostMapping("/{timesheetId}/approve")
    public ResponseEntity<?> approveTimesheet(
            @PathVariable Long timesheetId,
            @RequestParam(required = false) String remarks
    ) {
        try {
            Timesheet approvedTimesheet = timesheetService.approveTimesheet(timesheetId, remarks);
            return ResponseEntity.ok(approvedTimesheet);
        } catch (TimesheetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error approving timesheet");
        }
    }


    @PostMapping("/{timesheetId}/reject")
    public ResponseEntity<?> rejectTimesheet(
            @PathVariable Long timesheetId,
            @RequestParam(required = true) String remarks
    ) {
        try {
            Timesheet updated = timesheetService.rejectTimesheet(timesheetId, remarks);
            return ResponseEntity.ok(updated);
        } catch (TimesheetException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error rejecting timesheet");
        }
    }

    @GetMapping("/pending/{managerId}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<List<Timesheet>> listPending(@PathVariable Long managerId) {
        List<Timesheet> result = timesheetService.listPendingForManager(managerId);
        return ResponseEntity.ok(result);
    }


}
