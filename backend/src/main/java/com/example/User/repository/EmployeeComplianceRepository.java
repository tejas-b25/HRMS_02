package com.example.User.repository;

import com.example.User.entity.EmployeeComplianceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeComplianceRepository extends
        JpaRepository<EmployeeComplianceMapping, String> {
    List<EmployeeComplianceMapping> findByEmployeeId(Long employeeId);
    Optional<EmployeeComplianceMapping> findByEmployeeIdAndComplianceIdAndStatus
            (Long empId, Long compId, EmployeeComplianceMapping.Status status);
    @Query("""
SELECT emp.employeeId,
       cm.complianceName,
       cm.complianceType,
       m.status,
       m.startDate,
       m.endDate,
       cm.createdBy,
       m.remarks,
       cm.updatedAt
FROM EmployeeComplianceMapping m
JOIN Employee emp ON m.employeeId = emp.employeeId
JOIN ComplianceMaster cm ON m.complianceId = cm.complianceId
LEFT JOIN ComplianceDocument d ON d.complianceMappingId = m.complianceMappingId
WHERE m.createdAt BETWEEN :fromDate AND :toDate
""")
    List<Object[]> getComplianceSummaryData(LocalDateTime fromDate, LocalDateTime toDate);

}
