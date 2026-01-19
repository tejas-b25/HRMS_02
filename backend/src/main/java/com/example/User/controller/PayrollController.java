package com.example.User.controller;

import com.example.User.dto.EmployeePayrollRequestDto;
import com.example.User.dto.EmployeePayrollResponseDto;
import com.example.User.dto.SalaryStructureRequestDto;
import com.example.User.dto.SalaryStructureResponseDto;
import com.example.User.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @PostMapping("/structure")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<SalaryStructureResponseDto> createStructure(@RequestBody SalaryStructureRequestDto dto) {
        return ResponseEntity.ok(payrollService.createSalaryStructure(dto));
    }
    @PutMapping("/structure/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<SalaryStructureResponseDto> updateStructure(@PathVariable Long id,
                                                                      @RequestBody SalaryStructureRequestDto dto) {
        return ResponseEntity.ok(payrollService.updateSalaryStructure(id, dto));
    }
    @PostMapping("/process")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<EmployeePayrollResponseDto> processPayroll(@RequestBody EmployeePayrollRequestDto dto) {
        return ResponseEntity.ok(payrollService.processPayroll(dto));
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeePayrollResponseDto> getPayroll(@PathVariable Long employeeId,
                                                                 @RequestParam String month) {
        return ResponseEntity.ok(payrollService.getEmployeePayroll(employeeId, month));
    }
    @GetMapping("/report")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<EmployeePayrollResponseDto>> getAllPayrolls() {
        return ResponseEntity.ok(payrollService.getAllPayrolls());
    }
}
