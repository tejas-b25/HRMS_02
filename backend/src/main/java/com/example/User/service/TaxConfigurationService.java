package com.example.User.service;

import com.example.User.entity.TaxConfiguration;

import java.util.List;

public interface TaxConfigurationService {

    TaxConfiguration createTaxConfiguration(TaxConfiguration taxConfiguration);

    TaxConfiguration updateTaxConfiguration(Long id, TaxConfiguration taxConfiguration);

    List<TaxConfiguration> getAllConfigurations();

    TaxConfiguration getConfigurationById(Long id);


}
