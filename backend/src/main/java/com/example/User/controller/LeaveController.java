package com.example.User.controller;


import com.example.User.dto.LeaveDTO;
import com.example.User.entity.Leave;
import com.example.User.service.LeaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // 1. Apply for Leave
    private final ObjectMapper objectMapper;

    public LeaveController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('HR') or hasAnyRole('ADMIN')    or hasAnyRole('EMPLOYEE') ")
    public ResponseEntity<Object> applyLeave(
            @RequestPart("leave") String leaveJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        LeaveDTO request = objectMapper.readValue(leaveJson, LeaveDTO.class);

        Leave response = leaveService.applyLeave(request, file);
        return ResponseEntity.ok(response);
    }


    // 2. Get all leaves by Employee ID
    @GetMapping("/{employeeId}/list")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER','HR')")
    public ResponseEntity<List<Leave>> getLeaves(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveService.getLeavesByEmployee(employeeId));
    }

    // 3. Get Leave details by ID
    @GetMapping("/{leaveId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'HR')")
    public ResponseEntity<Leave> getLeaveById(@PathVariable Long leaveId) {
        Leave leave = leaveService.getLeaveById(leaveId);
        return ResponseEntity.ok(leave);
    }

    // 4. Approve or Reject a Leave
    @PutMapping("/approve/{leaveId}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','HR')")
    public ResponseEntity<Leave> approveLeave(
            @PathVariable Long leaveId,
            @RequestParam Boolean approve){
        Leave updatedLeave = leaveService.approveLeave(leaveId, approve);
        return ResponseEntity.ok(updatedLeave);
    }

    // 5. Cancel a Leave
    @PutMapping("/cancel/{leaveId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Leave> cancelLeave(
            @PathVariable Long leaveId,
            @RequestParam Long employeeId) {
        Leave cancelledLeave = leaveService.cancelLeave(leaveId, employeeId);
        return ResponseEntity.ok(cancelledLeave);
    }

    // 6. Get all leaves (for admin overview)
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<List<Leave>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }




}
