package com.example.User.controller;

import com.example.User.entity.LeaveType;
import com.example.User.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-types")
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;

    // 1️. Create Leave Type
    @PostMapping
    @PreAuthorize("hasAnyRole('HR', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<LeaveType> createLeaveType(@RequestBody LeaveType leaveType) {
        LeaveType created = leaveTypeService.createLeaveType(leaveType);
        return ResponseEntity.ok(created);
    }

    // 2️. Get All Leave Types
    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'HR', 'MANAGER','ADMIN')")
    public ResponseEntity<List<LeaveType>> getAllLeaveTypes() {
        List<LeaveType> types = leaveTypeService.getAllLeaveTypes();
        return ResponseEntity.ok(types);
    }

    // 3. Update existing Leave Type
    @PutMapping("/{leaveTypeId}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<LeaveType> updateLeaveType(
            @PathVariable("leaveTypeId") Long leaveTypeId,
            @RequestBody LeaveType leaveType) {
        LeaveType updated = leaveTypeService.updateLeaveType(leaveTypeId, leaveType);
        return ResponseEntity.ok(updated);
    }

    // 4. Delete a Leave Type
    @DeleteMapping("/{leaveTypeId}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<String> deleteLeaveType(@PathVariable Long leaveTypeId) {
        leaveTypeService.deleteLeaveType(leaveTypeId);
        return ResponseEntity.ok("Leave Type deleted successfully.");
    }




}