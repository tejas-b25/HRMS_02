package com.example.User.dto;

import com.example.User.entity.ComplianceMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceResponse {
    private Long complianceId;
    private String complianceName;
    private String complianceType;
    private String description;
    private Boolean isActive;

    public static ComplianceResponse fromEntity(ComplianceMaster c) {
        return ComplianceResponse.builder()
                .complianceId(c.getComplianceId())
                .complianceName(c.getComplianceName())
                .complianceType(c.getComplianceType().name())
                .description(c.getDescription())
                .isActive(c.getIsActive())
                .build();
    }
}
