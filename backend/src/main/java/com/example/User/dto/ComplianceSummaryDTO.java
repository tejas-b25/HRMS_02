package com.example.User.dto;

import com.example.User.entity.ComplianceMaster;
import com.example.User.entity.EmployeeComplianceMapping.Status;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class ComplianceSummaryDTO {
    private Long employeeId;
    private String complianceName;
    private ComplianceMaster.ComplianceType complianceType;
    private Status status;
    private LocalDate submittedDate;
    private LocalDate dueDate;
    private String verifiedBy;
    private String remarks;
    private LocalDateTime updatedAt;

    public ComplianceSummaryDTO(Long employeeId, String complianceName, ComplianceMaster.ComplianceType complianceType,
                                Status status, LocalDate submittedDate, LocalDate dueDate,
                                String verifiedBy, String remarks, LocalDateTime updatedAt) {
        this.employeeId = employeeId;
        this.complianceName = complianceName;
        this.complianceType = complianceType;
        this.status = status;
        this.submittedDate = submittedDate;
        this.dueDate = dueDate;
        this.verifiedBy = verifiedBy;
        this.remarks = remarks;
        this.updatedAt = updatedAt;
    }

}