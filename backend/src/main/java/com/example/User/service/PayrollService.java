package com.example.User.service;

import com.example.User.dto.EmployeePayrollRequestDto;
import com.example.User.dto.EmployeePayrollResponseDto;
import com.example.User.dto.SalaryStructureRequestDto;
import com.example.User.dto.SalaryStructureResponseDto;

import java.util.List;

public interface PayrollService {
    SalaryStructureResponseDto createSalaryStructure(SalaryStructureRequestDto dto);
    SalaryStructureResponseDto updateSalaryStructure(Long id, SalaryStructureRequestDto dto);
    EmployeePayrollResponseDto processPayroll(EmployeePayrollRequestDto dto);
    EmployeePayrollResponseDto getEmployeePayroll(Long employeeId, String month);
    List<EmployeePayrollResponseDto> getAllPayrolls();
}
