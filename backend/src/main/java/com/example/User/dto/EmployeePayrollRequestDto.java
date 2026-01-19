package com.example.User.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePayrollRequestDto {
    private Long employeeId;
    private String month;
    private Long structureId;
    private String bankReferenceNo;
}
