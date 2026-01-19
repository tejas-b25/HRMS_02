package com.example.User.serviceimpl;

import com.example.User.entity.BenefitMaster;
import com.example.User.entity.BenefitProvider;
import com.example.User.entity.Employee;
import com.example.User.entity.EmployeeBenefitMapping;
import com.example.User.repository.BenefitMasterRepository;
import com.example.User.repository.BenefitProviderRepository;
import com.example.User.repository.EmployeeBenefitMappingRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.service.EmployeeBenefitMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeBenefitMappingServiceImpl implements EmployeeBenefitMappingService {

    private final EmployeeBenefitMappingRepository mappingRepository;
    private final EmployeeRepository employeeRepository;
    private final BenefitMasterRepository benefitRepository;
    private final BenefitProviderRepository providerRepository;

    @Override
    public EmployeeBenefitMapping createMapping(EmployeeBenefitMapping mapping) {
        // Validate Employee
        Employee employee = employeeRepository.findById(Long.valueOf(mapping.getEmployeeId()))
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        // Validate Benefit
        BenefitMaster benefit = benefitRepository.findById(Long.valueOf(mapping.getBenefit().getBenefitId()))
                .orElseThrow(() -> new EntityNotFoundException("Benefit not found"));

        // Validate Provider
        BenefitProvider provider = providerRepository.findById(Long.valueOf(String.valueOf(mapping.getProvider().getProviderId())))
                .orElseThrow(() -> new EntityNotFoundException("Provider not found"));

        mapping.setEmployeeId(Long.valueOf(String.valueOf(employee.getEmployeeId())));
        mapping.setBenefit(benefit);
        mapping.setProvider(provider);

        return mappingRepository.save(mapping);
    }

    @Override
    public EmployeeBenefitMapping updateMapping(Long id, EmployeeBenefitMapping mapping) {
        EmployeeBenefitMapping existing = mappingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mapping not found"));

        existing.setCoverageAmount(mapping.getCoverageAmount());
        existing.setPremiumAmount(mapping.getPremiumAmount());
        existing.setEmployerContribution(mapping.getEmployerContribution());
        existing.setStartDate(mapping.getStartDate());
        existing.setEndDate(mapping.getEndDate());
        existing.setStatus(mapping.getStatus());

        if (mapping.getBenefit() != null) {
            BenefitMaster benefit = benefitRepository.findById(Long.valueOf(String.valueOf(mapping.getBenefit().getBenefitId())))
                    .orElseThrow(() -> new EntityNotFoundException("Benefit not found"));
            existing.setBenefit(benefit);
        }

        if (mapping.getProvider() != null) {
            BenefitProvider provider = providerRepository.findById(Long.valueOf(String.valueOf(mapping.getProvider().getProviderId())))
                    .orElseThrow(() -> new EntityNotFoundException("Provider not found"));
            existing.setProvider(provider);
        }

        return mappingRepository.save(existing);
    }

    @Override
    public void deleteMapping(Long id) {
        mappingRepository.deleteById(id);
    }

    @Override
    public EmployeeBenefitMapping getMappingById(Long id) {
        return mappingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mapping not found"));
    }

    @Override
    public List<EmployeeBenefitMapping> getAllMappings() {
        return mappingRepository.findAll();
    }

    @Override
    public List<EmployeeBenefitMapping> getMappingsByEmployeeId(Long employeeId) {
        return mappingRepository.findByEmployeeId(Long.valueOf(employeeId));
    }
}











