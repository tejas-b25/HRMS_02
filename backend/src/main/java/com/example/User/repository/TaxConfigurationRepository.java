package com.example.User.repository;

import com.example.User.entity.TaxConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TaxConfigurationRepository extends JpaRepository<TaxConfiguration, Long> {

    Optional<TaxConfiguration> findByRegimeTypeAndEffectiveFrom(TaxConfiguration.RegimeType regimeType, LocalDate effectiveFrom);
}