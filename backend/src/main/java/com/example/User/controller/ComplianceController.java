package com.example.User.controller;

import com.example.User.dto.ComplianceRequest;
import com.example.User.dto.ComplianceResponse;
import com.example.User.dto.EmployeeComplianceRequest;
import com.example.User.dto.EmployeeComplianceResponse;
import com.example.User.service.ComplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
public class ComplianceController {
    @Autowired
    private ComplianceService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<ComplianceResponse> createCompliance(@RequestBody ComplianceRequest req) {
        return ResponseEntity.ok(service.createCompliance(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<ComplianceResponse> updateCompliance(@PathVariable Long id,
                                                               @RequestBody ComplianceRequest req) {
        return ResponseEntity.ok(service.updateCompliance(id, req));
    }
    @GetMapping
    public ResponseEntity<List<ComplianceResponse>> getAll() {
        return ResponseEntity.ok(service.getAllCompliances());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCompliance(id);
        return ResponseEntity.noContent().build();
    }
    // Employee Compliance APIs
    @PostMapping("/employee")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeeComplianceResponse>
    assign(@RequestBody EmployeeComplianceRequest req) {
        return ResponseEntity.ok(service.assignCompliance(req));
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeComplianceResponse>> getEmployeeCompliances(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getEmployeeCompliances(employeeId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
