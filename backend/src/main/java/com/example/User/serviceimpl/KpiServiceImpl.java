package com.example.User.serviceimpl;

import com.example.User.dto.KpiMasterDto;
import com.example.User.entity.Department;
import com.example.User.entity.KpiMaster;
import com.example.User.repository.DepartmentRepository;
import com.example.User.repository.KpiMasterRepository;
import com.example.User.service.KpiService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class KpiServiceImpl implements KpiService {
    @Autowired
    private  KpiMasterRepository kpiMasterRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public KpiMasterDto createKpi(KpiMasterDto dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + dto.getDepartmentId()));

        boolean exists = kpiMasterRepository.existsByDepartment_DepartmentIdAndRoleAndKpiNameIgnoreCase(
                dto.getDepartmentId(), dto.getRole(), dto.getKpiName());

        if (exists) {
            throw new IllegalArgumentException("KPI already exists for this Department, Role, and Name.");
        }

        KpiMaster entity = KpiMaster.builder()
                .department(department)
                .role(dto.getRole())
                .kpiName(dto.getKpiName())
                .kpiDescription(dto.getKpiDescription())
                .weightage(dto.getWeightage())
                .build();

        entity = kpiMasterRepository.save(entity);

        dto.setKpiId(entity.getKpiId());
        return dto;
    }

    @Override
    public KpiMasterDto updateKpi(Long id, KpiMasterDto dto) {
        KpiMaster entity = kpiMasterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("KPI not found with id: " + id));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + dto.getDepartmentId()));

        entity.setDepartment(department);
        entity.setRole(dto.getRole());
        entity.setKpiName(dto.getKpiName());
        entity.setKpiDescription(dto.getKpiDescription());
        entity.setWeightage(dto.getWeightage());

        entity = kpiMasterRepository.save(entity);

        dto.setKpiId(entity.getKpiId());
        return dto;
    }

    @Override
    public KpiMasterDto getKpiById(Long id) {
        KpiMaster entity = kpiMasterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("KPI not found with id: " + id));

        return KpiMasterDto.builder()
                .kpiId(entity.getKpiId())
                .departmentId(entity.getDepartment().getDepartmentId())
                .role(entity.getRole())
                .kpiName(entity.getKpiName())
                .kpiDescription(entity.getKpiDescription())
                .weightage(entity.getWeightage())
                .build();
    }

    @Override
    public List<KpiMasterDto> getAllKpi() {
        return kpiMasterRepository.findAll().stream()
                .map(entity -> KpiMasterDto.builder()
                        .kpiId(entity.getKpiId())
                        .departmentId(entity.getDepartment().getDepartmentId())
                        .role(entity.getRole())
                        .kpiName(entity.getKpiName())
                        .kpiDescription(entity.getKpiDescription())
                        .weightage(entity.getWeightage())
                        .build())
                .toList();
    }
    @Override
    public void deleteKpi(Long id) {
        KpiMaster entity = kpiMasterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("KPI not found with id: " + id));

        kpiMasterRepository.delete(entity);
    }
}
