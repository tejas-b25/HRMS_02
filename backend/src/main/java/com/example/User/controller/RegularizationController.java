package com.example.User.controller;

import com.example.User.dto.RegularizationRequestDTO;
import com.example.User.dto.RegularizationResponseDTO;
import com.example.User.service.RegularizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regularization")
@CrossOrigin("*")
public class RegularizationController {

    @Autowired
    private RegularizationService service;

    @PostMapping("/create")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<RegularizationResponseDTO> create(@RequestBody RegularizationRequestDTO dto) {
        return ResponseEntity.ok(service.createRequest(dto));
    }

    //Get By Id
    @GetMapping("/{id}")
    public ResponseEntity<RegularizationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    //Get All
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    public ResponseEntity<List<RegularizationResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    //Get By Employee Id
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<RegularizationResponseDTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getByEmployeeId(employeeId));
    }

    //Approve api
    @PutMapping("/approve/{id}/{approverId}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    public ResponseEntity<RegularizationResponseDTO> approve(
            @PathVariable Long id,
            @PathVariable Long approverId) {
        return ResponseEntity.ok(service.approve(id, approverId));
    }

    //Reject Api
    @PutMapping("/reject/{id}/{approverId}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','MANAGER')")
    public ResponseEntity<RegularizationResponseDTO> reject(
            @PathVariable Long id,
            @PathVariable Long approverId) {
        return ResponseEntity.ok(service.reject(id, approverId));
    }
}
