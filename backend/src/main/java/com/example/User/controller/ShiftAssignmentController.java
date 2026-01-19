package com.example.User.controller;

import com.example.User.dto.ShiftAssignmentDTO;
import com.example.User.service.ShiftAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shifts")
public class ShiftAssignmentController {

    @Autowired
    private ShiftAssignmentService shiftAssignmentService;

    @PostMapping("/assign")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    public ResponseEntity<ShiftAssignmentDTO> createShiftAssignment(@RequestBody ShiftAssignmentDTO dto) {
        return ResponseEntity.ok(shiftAssignmentService.createShiftAssignment(dto));
    }

    // Get assigned shift by employee ID
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<ShiftAssignmentDTO> getShiftByEmployeeId(@PathVariable Long employeeId) {
        ShiftAssignmentDTO shiftAssignment = shiftAssignmentService.getShiftByEmployeeId(employeeId);
        return ResponseEntity.ok(shiftAssignment);
    }
}
