package com.example.User.repository;

import com.example.User.entity.BenefitMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitMasterRepository extends JpaRepository<BenefitMaster, Long> {
    boolean existsByBenefitName(String benefitName);
}
