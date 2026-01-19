package com.example.User.repository;

import com.example.User.entity.ComplianceMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplianceRepository extends JpaRepository<ComplianceMaster, Long>{
    Optional<ComplianceMaster> findByComplianceNameAndComplianceType
            (String name, ComplianceMaster.ComplianceType type);


}
