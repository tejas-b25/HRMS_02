package com.example.User.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeComplianceRequest {
    private Long employeeId;
    private Long complianceId;
    private String complianceNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String remarks;
    private String createdBy;
}
