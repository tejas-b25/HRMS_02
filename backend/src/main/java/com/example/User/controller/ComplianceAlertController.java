package com.example.User.controller;

import com.example.User.dto.ComplianceAlertRequestDto;
import com.example.User.entity.ComplianceAlert;
import com.example.User.service.ComplianceAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/compliance/alerts")
public class ComplianceAlertController {
    @Autowired
    private ComplianceAlertService alertService;
    @PostMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<ComplianceAlert> createAlert(@RequestBody ComplianceAlertRequestDto request) {
        return ResponseEntity.ok(alertService.createAlert(request));
    }

    @GetMapping("/{alertId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER','EMPLOYEE')")
    public ResponseEntity<ComplianceAlert> getAlertById(@PathVariable Long alertId) {
        ComplianceAlert alert = alertService.getAlertById(alertId);
        return ResponseEntity.ok(alert);
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    public ResponseEntity<List<ComplianceAlert>> getAllAlerts() {
        List<ComplianceAlert> alerts = alertService.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/upcoming")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    public ResponseEntity<List<ComplianceAlert>> getUpcomingAlerts(
            @RequestParam(defaultValue = "7") int days) {
        List<ComplianceAlert> upcomingAlerts = alertService.getUpcomingAlerts(days);
        return ResponseEntity.ok(upcomingAlerts);
    }
    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    public ResponseEntity<List<ComplianceAlert>> getAlertHistory(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String channel,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        List<ComplianceAlert> history = alertService.getAlertHistory(status, channel, fromDate, toDate);
        return ResponseEntity.ok(history);
    }
    @DeleteMapping("/{alertId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> deleteAlert(@PathVariable Long alertId) {
        boolean deleted = alertService.deleteAlert(alertId);
        if (deleted) {
            return ResponseEntity.ok("Compliance alert deleted/cancelled successfully.");
        } else {
            return ResponseEntity.status(404).body("Alert not found with ID: " + alertId);
        }
    }
    @PutMapping("/{alertId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<ComplianceAlert> updateAlert(
            @PathVariable Long alertId,
            @RequestBody ComplianceAlertRequestDto request
    ) {
        ComplianceAlert updatedAlert = alertService.updateAlert(alertId, request);
        return ResponseEntity.ok(updatedAlert);
    }
}
