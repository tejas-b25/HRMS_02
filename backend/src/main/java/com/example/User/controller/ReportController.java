package com.example.User.controller;

import com.example.User.service.ReportService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController

@RequestMapping("/api/reports")

@RequiredArgsConstructor

public class ReportController {

    private final ReportService reportService;

    @PostMapping("/export")

    @PreAuthorize("hasAnyRole('HR','MANAGER','ADMIN')")

    public ResponseEntity<?> exportReport(@RequestParam String reportType,

                                          @RequestParam String exportType,

                                          @RequestParam(required = false) String fromDate,

                                          @RequestParam(required = false) String toDate) {

        try {

            if (reportType.equalsIgnoreCase("EmployeeMaster")) {

                String filePath = reportService.exportEmployeeMasterReport(exportType);

                return ResponseEntity.ok("EmployeeMaster Report generated. File: " + filePath);

            }

            if (reportType.equalsIgnoreCase("Attendance")) {

                if (fromDate == null || toDate == null) {

                    return ResponseEntity.badRequest().body("fromDate and toDate are required for Attendance report.");

                }

                LocalDate start = LocalDate.parse(fromDate);

                LocalDate end = LocalDate.parse(toDate);

                String filePath = reportService.exportAttendanceReport(exportType, start, end);

                return ResponseEntity.ok("Attendance Report generated. File: " + filePath);

            }

            if (reportType.equalsIgnoreCase("ComplianceSummary")) {

                if (fromDate == null || toDate == null) {

                    return ResponseEntity.badRequest().body("fromDate and toDate are required for Compliance Summary report.");

                }

                LocalDate start = LocalDate.parse(fromDate);

                LocalDate end = LocalDate.parse(toDate);

                String filePath = reportService.exportComplianceSummary(exportType, start, end);

                return ResponseEntity.ok("Compliance Summary Report generated. File: " + filePath);

            }

            if (reportType.equalsIgnoreCase("Leave")) {

                if (fromDate == null || toDate == null) {

                    return ResponseEntity.badRequest().body("fromDate and toDate are required for Leave report.");

                }

                LocalDate start = LocalDate.parse(fromDate);

                LocalDate end = LocalDate.parse(toDate);

                String filePath = reportService.exportLeaveReport(exportType, start, end);

                return ResponseEntity.ok("Leave Report generated. File: " + filePath);

            }

            return ResponseEntity.badRequest().body(

                    "Invalid reportType! Valid values: EmployeeMaster, Attendance, ComplianceSummary, Leave."

            );

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body("Error generating report: " + e.getMessage());

        }

    }

}

