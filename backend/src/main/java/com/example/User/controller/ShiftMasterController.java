package com.example.User.controller;


import com.example.User.dto.ShiftMasterDTO;
import com.example.User.service.ShiftMasterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftMasterController {

    private final ShiftMasterService shiftMasterService;

    public ShiftMasterController(ShiftMasterService shiftMasterService) {
        this.shiftMasterService = shiftMasterService;
    }

    // Create Shift
    @PostMapping
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<ShiftMasterDTO> createShift(@Valid @RequestBody ShiftMasterDTO shiftDTO) {
        ShiftMasterDTO created = shiftMasterService.createShift(shiftDTO);
        return ResponseEntity.ok(created);
    }

    // Get shift Id
    @GetMapping("/{shiftId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER','EMPLOYEE')")
    public ResponseEntity<ShiftMasterDTO> getShiftById(@PathVariable Long shiftId) {
        return ResponseEntity.ok(shiftMasterService.getShiftById(shiftId));
    }

    // Get All Shifts
    @GetMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER','EMPLOYEE')")
    public ResponseEntity<List<ShiftMasterDTO>> getAllShifts() {
        return ResponseEntity.ok(shiftMasterService.getAllShifts());
    }

    // Delete Shift by Shift id
    @DeleteMapping("/{shiftId}")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteShift(@PathVariable Long shiftId) {
        shiftMasterService.deleteShift(shiftId);
        return ResponseEntity.ok("Shift deleted successfully");
    }

    // Update
    @PutMapping("/{shiftId}")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<ShiftMasterDTO> updateShift(@PathVariable Long shiftId,
                                                      @RequestBody ShiftMasterDTO shiftDTO) {
        return ResponseEntity.ok(shiftMasterService.updateShift(shiftId, shiftDTO));
    }



}
