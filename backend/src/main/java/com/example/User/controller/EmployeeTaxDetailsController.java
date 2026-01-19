package com.example.User.controller;

import com.example.User.entity.EmployeeTaxDetails;
import com.example.User.service.EmployeeTaxDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tax/employee")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeTaxDetailsController {

    @Autowired
    private EmployeeTaxDetailsService taxDetailsService;

    @PostMapping
    public ResponseEntity<EmployeeTaxDetails> assignOrUpdate(@RequestBody EmployeeTaxDetails taxDetails) {
        return ResponseEntity.ok(taxDetailsService.assignOrUpdateEmployeeTax(taxDetails));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<List<EmployeeTaxDetails>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(taxDetailsService.getTaxDetailsByEmployeeId(employeeId));
    }
}
