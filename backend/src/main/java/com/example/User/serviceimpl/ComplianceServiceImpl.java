package com.example.User.serviceimpl;

import com.example.User.dto.ComplianceRequest;
import com.example.User.dto.ComplianceResponse;
import com.example.User.dto.EmployeeComplianceRequest;
import com.example.User.dto.EmployeeComplianceResponse;
import com.example.User.entity.ComplianceMaster;
import com.example.User.entity.EmployeeComplianceMapping;
import com.example.User.exception.DuplicateRecordException;
import com.example.User.exception.InvalidDataException;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.ComplianceRepository;
import com.example.User.repository.EmployeeComplianceRepository;
import com.example.User.service.ComplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplianceServiceImpl implements ComplianceService {
    @Autowired
    private  ComplianceRepository complianceRepo;
    @Autowired
    private  EmployeeComplianceRepository empComplianceRepo;
    @Override
    public ComplianceResponse createCompliance(ComplianceRequest request) {
        complianceRepo.findByComplianceNameAndComplianceType(request.getComplianceName(), request.getComplianceType())
                .ifPresent(c -> { throw new DuplicateRecordException("Duplicate compliance under same type."); });

        ComplianceMaster compliance = ComplianceMaster.builder()
                .complianceName(request.getComplianceName())
                .complianceType(request.getComplianceType())
                .description(request.getDescription())
                .isActive(true)
                .createdBy(request.getCreatedBy())
                .build();

        complianceRepo.save(compliance);
        return ComplianceResponse.fromEntity(compliance);
    }

    @Override
    public ComplianceResponse updateCompliance(Long id, ComplianceRequest request) {
        ComplianceMaster existing = complianceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance not found"));
        existing.setDescription(request.getDescription());
        existing.setIsActive(request.getIsActive());
        existing.setUpdatedBy(request.getUpdatedBy());
        complianceRepo.save(existing);
        return ComplianceResponse.fromEntity(existing);
    }

    @Override
    public List<ComplianceResponse> getAllCompliances() {
        return complianceRepo.findAll()
                .stream().map(ComplianceResponse::fromEntity).collect(Collectors.toList());
    }

//    @Override
//    public void deleteCompliance(String id) {
//
//    }

    @Override
    public void deleteCompliance(Long id) {
        complianceRepo.deleteById(id);
    }

    @Override
    public EmployeeComplianceResponse assignCompliance(EmployeeComplianceRequest request) {
        ComplianceMaster compliance = complianceRepo.findById(request.getComplianceId())
                .orElseThrow(() -> new ResourceNotFoundException("Compliance not found"));

        if (!compliance.getIsActive())
            throw new InvalidDataException("Inactive compliance cannot be assigned.");

        if (request.getStartDate().isAfter(request.getEndDate()))
            throw new InvalidDataException("Start date cannot be after end date.");

        empComplianceRepo.findByEmployeeIdAndComplianceIdAndStatus(request.getEmployeeId(), request.getComplianceId(),
                        EmployeeComplianceMapping.Status.Active)
                .ifPresent(e -> { throw new DuplicateRecordException("Active compliance already exists for employee."); });

        EmployeeComplianceMapping mapping = EmployeeComplianceMapping.builder()
                .employeeId(request.getEmployeeId())
                .complianceId(request.getComplianceId())
                .complianceNumber(request.getComplianceNumber())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(EmployeeComplianceMapping.Status.Active)
                .remarks(request.getRemarks())
                .createdBy(request.getCreatedBy())
                .build();

        empComplianceRepo.save(mapping);
        return EmployeeComplianceResponse.fromEntity(mapping);
    }

    @Override
    public List<EmployeeComplianceResponse> getEmployeeCompliances(Long employeeId) {

        return empComplianceRepo.findByEmployeeId(employeeId)
                .stream().map(EmployeeComplianceResponse::fromEntity).collect(Collectors.toList());
    }

    @Override
    public ComplianceResponse getById(Long id) {
        ComplianceMaster compliance = complianceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance not found with id: " + id));
        return ComplianceResponse.fromEntity(compliance);
    }
}


