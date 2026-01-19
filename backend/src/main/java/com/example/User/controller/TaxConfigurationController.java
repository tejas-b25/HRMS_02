package com.example.User.controller;

import com.example.User.entity.TaxConfiguration;
import com.example.User.service.TaxConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tax/config")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TaxConfigurationController {

    @Autowired
    private TaxConfigurationService taxService;

    @PostMapping
    public ResponseEntity<TaxConfiguration> createConfig(@RequestBody TaxConfiguration taxConfiguration) {
        return ResponseEntity.ok(taxService.createTaxConfiguration(taxConfiguration));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxConfiguration> updateTaxConfiguration(
            @PathVariable Long id,
            @RequestBody TaxConfiguration taxConfiguration) {
        return ResponseEntity.ok(taxService.updateTaxConfiguration(id, taxConfiguration));
    }

    @GetMapping
    public ResponseEntity<List<TaxConfiguration>> getAllConfigs() {
        return ResponseEntity.ok(taxService.getAllConfigurations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxConfiguration> getConfig(@PathVariable Long id) {
        return ResponseEntity.ok(taxService.getConfigurationById(id));
    }


}
