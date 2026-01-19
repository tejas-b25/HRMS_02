package com.example.User.service;

import com.example.User.entity.EmployeeTaxDetails;

import java.util.List;

public interface EmployeeTaxDetailsService {
    EmployeeTaxDetails assignOrUpdateEmployeeTax(EmployeeTaxDetails taxDetails);

    List<EmployeeTaxDetails> getTaxDetailsByEmployeeId(Long employeeId);

    EmployeeTaxDetails getTaxDetailsById(Long taxId);
}
