package com.example.User.repository;

import com.example.User.entity.ComplianceDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceDocumentRepository extends
        JpaRepository<ComplianceDocument, Long> {

    List<ComplianceDocument> findByComplianceMappingId(Long complianceMappingId);
    List<ComplianceDocument> findByExpiryDateBefore(java.time.LocalDate date);
    //List<ComplianceDocument> findByEmployeeId(Long employeeId);

    List<ComplianceDocument> findByEmployeeId(Long employeeId);
}
