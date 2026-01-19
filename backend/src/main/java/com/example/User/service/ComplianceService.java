package com.example.User.service;

import com.example.User.dto.ComplianceRequest;
import com.example.User.dto.ComplianceResponse;
import com.example.User.dto.EmployeeComplianceRequest;
import com.example.User.dto.EmployeeComplianceResponse;

import java.util.List;

public interface ComplianceService {
    ComplianceResponse createCompliance(ComplianceRequest request);
    ComplianceResponse updateCompliance(Long id, ComplianceRequest request);
    List<ComplianceResponse> getAllCompliances();
    void deleteCompliance(Long id);
    EmployeeComplianceResponse assignCompliance(EmployeeComplianceRequest request);
    List<EmployeeComplianceResponse> getEmployeeCompliances(Long employeeId);
    ComplianceResponse getById(Long id);
}
