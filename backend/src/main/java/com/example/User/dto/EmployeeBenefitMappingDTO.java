package com.example.User.dto;

import com.example.User.entity.BenefitMaster;
import com.example.User.entity.BenefitProvider;
import com.example.User.entity.EmployeeStatus;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeBenefitMappingDTO {
    private Long mappingId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;  // FK to employee_master

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefit_id")
    private BenefitMaster benefit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private BenefitProvider provider;
    private BigDecimal coverageAmount;
    private BigDecimal premiumAmount;
    private BigDecimal employerContribution;
    private LocalDate startDate;
    private LocalDate endDate;
    private EmployeeStatus status;
}

