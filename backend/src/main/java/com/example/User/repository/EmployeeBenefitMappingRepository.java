package com.example.User.repository;

import com.example.User.entity.EmployeeBenefitMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeBenefitMappingRepository extends JpaRepository<EmployeeBenefitMapping, Long> {
    List<EmployeeBenefitMapping> findByEmployeeId(Long employeeId);

    List<EmployeeBenefitMapping> findByBenefit_BenefitId(Long benefitId);

    List<EmployeeBenefitMapping> findByProvider_ProviderId(Long providerId);
}

