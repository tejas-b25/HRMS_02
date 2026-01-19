package com.example.User.controller;

import com.example.User.dto.BenefitProviderDTO;
import com.example.User.entity.BenefitProvider;
import com.example.User.service.BenefitProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class BenefitProviderController {
    @Autowired
    private BenefitProviderService providerService;

    // Create Provider
    @PostMapping
    public ResponseEntity<BenefitProviderDTO> createProvider(@RequestBody BenefitProviderDTO dto) {
        return ResponseEntity.ok(providerService.createProvider(dto));
    }

    // Get Provider by ID
    @GetMapping("/{providerId}")
    public ResponseEntity<BenefitProviderDTO> getProviderById(@PathVariable Long providerId) {
        BenefitProviderDTO provider = providerService.getProviderById(providerId);
        return ResponseEntity.ok(provider);
    }

    // Get All Providers
    @GetMapping
    public ResponseEntity<List<BenefitProviderDTO>> getAllProviders() {
        return ResponseEntity.ok(providerService.getAllProviders());
    }

    // Update Provider
    @PutMapping("/{providerId}")
    public ResponseEntity<BenefitProvider> updateProvider(@PathVariable Long providerId,
                                                             @RequestBody BenefitProvider entity) {
        return ResponseEntity.ok(providerService.updateProvider(providerId, entity));
    }

    // Delete Provider
    @DeleteMapping("/{providerId}")
    public ResponseEntity<String> deleteProvider(@PathVariable Long providerId) {
        providerService.deleteProvider(providerId);
        return ResponseEntity.ok("Provider deleted successfully.");
    }



    }


