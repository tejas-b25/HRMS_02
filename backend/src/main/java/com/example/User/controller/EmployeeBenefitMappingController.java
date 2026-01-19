package com.example.User.controller;


import com.example.User.entity.EmployeeBenefitMapping;
import com.example.User.service.EmployeeBenefitMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-benefits")
@RequiredArgsConstructor
public class EmployeeBenefitMappingController {

    private final EmployeeBenefitMappingService mappingService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<EmployeeBenefitMapping> createMapping(@RequestBody EmployeeBenefitMapping mapping) {
        return ResponseEntity.ok(mappingService.createMapping(mapping));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<EmployeeBenefitMapping> updateMapping(@PathVariable Long id,
                                                                @RequestBody EmployeeBenefitMapping mapping) {
        return ResponseEntity.ok(mappingService.updateMapping(id, mapping));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMapping(@PathVariable Long id) {
        mappingService.deleteMapping(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
    //@PreAuthorize("@securityService.canAccessEmployee(#employeeId, authentication) || @securityService.canAccessHr(#userId, authentication)")
    public ResponseEntity<EmployeeBenefitMapping> getMappingById(@PathVariable Long id) {
        return ResponseEntity.ok(mappingService.getMappingById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<List<EmployeeBenefitMapping>> getAllMappings() {
        return ResponseEntity.ok(mappingService.getAllMappings());
    }

//    @GetMapping("/employee/{employeeId}")
//     @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
//    //@PreAuthorize("@securityService.canAccessEmployee(#employeeId, authentication) || @securityService.canAccessHr(#userId, authentication)")
//    public ResponseEntity<List<EmployeeBenefitMapping>> getMappingsByEmployee(@PathVariable Long employeeId) {
//        return ResponseEntity.ok(mappingService.getMappingsByEmployeeId(employeeId));
//    }
}

