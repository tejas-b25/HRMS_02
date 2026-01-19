package com.example.User.controller;

import com.example.User.dto.BenefitMasterDTO;
import com.example.User.dto.ShiftMasterDTO;
import com.example.User.service.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/benefits")
public class BenefitController {
    @Autowired
    private BenefitService benefitService;

    // Create Benefit
    @PostMapping
    public ResponseEntity<BenefitMasterDTO> createBenefit(@RequestBody BenefitMasterDTO dto) {
        return ResponseEntity.ok(benefitService.createBenefit(dto));
    }

    // Get Benefit By ID
    @GetMapping("/{benefitId}")
    public ResponseEntity<BenefitMasterDTO> getBenefitById(@PathVariable Long benefitId) {
        return ResponseEntity.ok(benefitService.getBenefitById(benefitId));
    }

    // Get All Benefits
    @GetMapping
    public ResponseEntity<List<BenefitMasterDTO>> getAllBenefits() {
        return ResponseEntity.ok(benefitService.getAllBenefits());
    }

    // Update Benefit
    @PutMapping("/{benefitId}")
    public ResponseEntity<BenefitMasterDTO> updateBenefit(@PathVariable Long benefitId,
                                                          @RequestBody BenefitMasterDTO dto) {
        return ResponseEntity.ok(benefitService.updateBenefit(benefitId, dto));
    }

    // Delete Benefit
    @DeleteMapping("/{benefitId}")
    public ResponseEntity<String> deleteBenefit(@PathVariable Long benefitId) {
        benefitService.deleteBenefit(benefitId);
        return ResponseEntity.ok("Benefit deleted successfully.");
    }


}
