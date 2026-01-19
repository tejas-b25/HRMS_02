package com.example.User.serviceimpl;

import com.example.User.entity.TaxConfiguration;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.TaxConfigurationRepository;
import com.example.User.service.TaxConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxConfigurationServiceImpl implements TaxConfigurationService {
    @Autowired
    private TaxConfigurationRepository taxRepository;

    @Override
    public TaxConfiguration createTaxConfiguration(TaxConfiguration taxConfiguration) {
        taxRepository.findByRegimeTypeAndEffectiveFrom(taxConfiguration.getRegimeType(), taxConfiguration.getEffectiveFrom())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Duplicate tax configuration for regime and effective date");
                });
        return taxRepository.save(taxConfiguration);
    }

    @Override
    public TaxConfiguration updateTaxConfiguration(Long id, TaxConfiguration taxConfiguration) {
        TaxConfiguration existing = taxRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax Configuration not found"));
        existing.setRebateLimit(taxConfiguration.getRebateLimit());
        existing.setExemptionRules(taxConfiguration.getExemptionRules());
        existing.setEffectiveFrom(taxConfiguration.getEffectiveFrom());
        return taxRepository.save(existing);
    }

    @Override
    public List<TaxConfiguration> getAllConfigurations() {
        return taxRepository.findAll();
    }

    @Override
    public TaxConfiguration getConfigurationById(Long id) {
        return taxRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax Configuration not found"));
    }
}



