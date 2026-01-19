package com.example.User.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructureResponseDto {
    private Long structureId;
    private String structureName;
    private Double basicPay;
    private Double hra;
    private Double variablePay;
    private Double otherAllowances;
    private Double deductions;
}
