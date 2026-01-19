package com.example.User.dto;

import com.example.User.entity.EmployeeComplianceMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmployeeComplianceResponse {
    private Long complianceMappingId;
    private Long employeeId;
    private Long complianceId;
    private String complianceNumber;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    public static EmployeeComplianceResponse fromEntity(EmployeeComplianceMapping e) {
        return EmployeeComplianceResponse.builder()
                .complianceMappingId(e.getComplianceMappingId())
                .employeeId(e.getEmployeeId())
                .complianceId(e.getComplianceId())
                .complianceNumber(e.getComplianceNumber())
                .status(e.getStatus().name())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .build();
    }
}
