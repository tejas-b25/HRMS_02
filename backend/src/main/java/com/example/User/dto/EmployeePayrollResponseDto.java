package com.example.User.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayrollResponseDto {
    private Long payrollId;
    private Long employeeId;
    private String month;
    private String structureName;
    private Double grossSalary;
    private Double totalDeductions;
    private Double netSalary;
    private String paymentStatus;
    private String bankReferenceNo;
}
