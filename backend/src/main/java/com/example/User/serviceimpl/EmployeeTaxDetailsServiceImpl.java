package com.example.User.serviceimpl;

import com.example.User.entity.EmployeeTaxDetails;
import com.example.User.entity.TaxConfiguration;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.EmployeeTaxDetailsRepository;
import com.example.User.repository.TaxConfigurationRepository;
import com.example.User.service.EmployeeTaxDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeTaxDetailsServiceImpl implements EmployeeTaxDetailsService {
    @Autowired
    private EmployeeTaxDetailsRepository taxDetailsRepository;
    @Autowired
    private  TaxConfigurationRepository taxConfigRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeTaxDetails assignOrUpdateEmployeeTax(EmployeeTaxDetails taxDetails) {
        employeeRepository.findById(taxDetails.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + taxDetails.getEmployeeId()));


        TaxConfiguration config = taxConfigRepository.findById(
                taxDetails.getTaxConfiguration().getTaxConfigId()
        ).orElseThrow(() -> new ResourceNotFoundException("Tax Configuration not found"));

        taxDetails.setTaxConfiguration(config);
        return taxDetailsRepository.save(taxDetails);
    }


    @Override
    public List<EmployeeTaxDetails> getTaxDetailsByEmployeeId(Long employeeId) {
        return taxDetailsRepository.findByEmployeeId(Long.valueOf(String.valueOf(employeeId)));
    }

    @Override
    public EmployeeTaxDetails getTaxDetailsById(Long taxId) {
        return taxDetailsRepository.findById(Long.valueOf(String.valueOf(taxId)))
                .orElseThrow(() -> new ResourceNotFoundException("Employee Tax Details not found"));
    }
}
