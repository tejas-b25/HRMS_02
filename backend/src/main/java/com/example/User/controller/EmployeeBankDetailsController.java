package com.example.User.controller;

import com.example.User.dto.EmployeeBankDetailsRequestDto;
import com.example.User.dto.EmployeeBankDetailsResponseDto;
import com.example.User.service.EmployeeBankDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employeeBankDetails")
@RequiredArgsConstructor
public class EmployeeBankDetailsController {

    private final EmployeeBankDetailsService bankService;

    // Create Bank Details
    @PostMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    @CrossOrigin("*")
    public ResponseEntity<EmployeeBankDetailsResponseDto> create(
            @RequestBody EmployeeBankDetailsRequestDto dto) {
        return ResponseEntity.ok(bankService.createBankDetails(dto));
    }


    // Get by BankId
    @GetMapping("/{bankId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeeBankDetailsResponseDto> getByBankId(
            @PathVariable Long bankId) {
        return ResponseEntity.ok(bankService.getBankDetailsById(bankId));
    }

    // Get by EmployeeId
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeeBankDetailsResponseDto> getByEmployeeId(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(bankService.getBankDetailsByEmployeeId(employeeId));
    }

    // Update Bank Details
    @PutMapping("/{bankId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeeBankDetailsResponseDto> update(
            @PathVariable Long bankId,
            @RequestBody EmployeeBankDetailsRequestDto dto) {
        return ResponseEntity.ok(bankService.updateBankDetails(bankId, dto));
    }

    // Soft Delete Bank Details
    @DeleteMapping("/{bankId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long bankId) {
        return ResponseEntity.ok(bankService.deleteBankDetails(bankId));
    }

    // Hard Delete Bank Details
    @DeleteMapping("/hard/{bankId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<String> hardDelete(@PathVariable Long bankId) {
        return ResponseEntity.ok(bankService.hardDeleteBankDetails(bankId));
    }

}

