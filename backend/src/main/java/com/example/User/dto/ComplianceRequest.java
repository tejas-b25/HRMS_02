package com.example.User.dto;

import com.example.User.entity.ComplianceMaster;
import lombok.Data;

@Data
public class ComplianceRequest {
    private String complianceName;
    private ComplianceMaster.ComplianceType complianceType;
    private String description;
    private Boolean isActive;
    private String createdBy;
    private String updatedBy;
}
