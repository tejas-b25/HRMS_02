package com.example.User.serviceimpl;

import com.example.User.dto.DepartmentCreateDto;
import com.example.User.dto.DepartmentResponseDto;
import com.example.User.dto.DepartmentUpdateDto;
import com.example.User.entity.AuditLog;
import com.example.User.entity.Department;
import com.example.User.entity.DepartmentStatus;
import com.example.User.exception.BadRequestException;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.AuditLogRepository;
import com.example.User.repository.DepartmentRepository;
import com.example.User.service.AuditLogService;
import com.example.User.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private AuditLogService auditLogService;


    private void saveAudit(String action, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setPerformedBy("system"); // later: SecurityContext se logged-in user
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(details);
        auditLogRepository.save(log);
    }


    private DepartmentResponseDto mapToDto(Department d) {
        DepartmentResponseDto r = new DepartmentResponseDto();
        r.setDepartmentId(d.getDepartmentId());
        r.setName(d.getName());
        r.setCode(d.getCode());
        r.setDescription(d.getDescription());
        r.setStatus(d.getStatus().name());
        r.setCreatedAt(d.getCreatedAt());
        r.setUpdatedAt(d.getUpdatedAt());
        return r;
    }


    @Override
    public DepartmentResponseDto createDepartment(DepartmentCreateDto dto) {
        if (departmentRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestException("Department name already exists");
        }
        if (departmentRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new BadRequestException("Department code already exists");
        }
        Department d = new Department();
        d.setName(dto.getName().trim());
        d.setCode(dto.getCode().trim().toUpperCase());
        d.setDescription(dto.getDescription());
        d.setStatus(DepartmentStatus.ACTIVE);
        Department saved = departmentRepository.save(d);
        // Dept save Audit log
        saveAudit("CREATE_DEPARTMENT", "Created: " + saved.getName());
        return mapToDto(saved);
    }

    @Override
    public DepartmentResponseDto updateDepartment(Long id, DepartmentUpdateDto dto) {

        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));

        // Update Name
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            String newName = dto.getName().trim();
            if (!newName.equalsIgnoreCase(dept.getName()) &&
                    departmentRepository.existsByNameIgnoreCase(newName)) {
                throw new BadRequestException("Department name already exists");
            }
            dept.setName(newName);
        }

        // Update Code
        if (dto.getCode() != null && !dto.getCode().trim().isEmpty()) {
            String newCode = dto.getCode().trim().toUpperCase();
            if (!newCode.equalsIgnoreCase(dept.getCode()) &&
                    departmentRepository.existsByCodeIgnoreCase(newCode)) {
                throw new BadRequestException("Department code already exists");
            }
            dept.setCode(newCode);
        }

        // Update Description
        if (dto.getDescription() != null) {
            dept.setDescription(dto.getDescription().trim());
        }

        // Update Status
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            try {
                dept.setStatus(DepartmentStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new BadRequestException("Invalid status value: must be ACTIVE or INACTIVE");
            }
        }

        Department saved = departmentRepository.save(dept);

        saveAudit("DEPARTMENT_UPDATED", "Updated Department: " + saved.getName());

        return mapToDto(saved);
    }


    // get BYID
    @Override
    public DepartmentResponseDto getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

        return mapToDto(department);
    }


    @Override
    public Page<DepartmentResponseDto> listDepartments(Pageable pageable, String statusFilter) {
        Page<Department> page;
        if (statusFilter == null || statusFilter.isBlank()) {
            page = departmentRepository.findAll(pageable);
        } else {
            DepartmentStatus st;
            try {
                st = DepartmentStatus.valueOf(statusFilter.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new BadRequestException("Invalid status filter");
            }
            page = departmentRepository.findAllByStatus(st, pageable);
        }
        return page.map(this::mapToDto);
    }

    @Override
    public void softDeleteDepartment(Long id) {
        Department d = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));

        d.setStatus(DepartmentStatus.INACTIVE);
        d.setUpdatedAt(LocalDateTime.now());
        departmentRepository.save(d);

        saveAudit("DEPARTMENT_DELETED", "Deleted: " + d.getName());
    }


// Added new API

    @Override
    public List<DepartmentResponseDto> searchDepartments(String query) {
        return departmentRepository.searchDepartments(query)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentResponseDto> filterDepartmentsByStatus(DepartmentStatus status) {
        return departmentRepository.findByStatus(status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentResponseDto> searchDepartmentsByStatus(String query, DepartmentStatus status) {
        return departmentRepository.searchDepartmentsByStatus(query, status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    @Override
    public DepartmentResponseDto updateDepartmentStatus(Long id, String status, String performedBy) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));

        DepartmentStatus newStatus = DepartmentStatus.valueOf(status.toUpperCase());
        department.setStatus(newStatus);
        department.setUpdatedAt(LocalDateTime.now());

        departmentRepository.save(department);

        auditLogService.saveAudit(
                performedBy,
                "DEPARTMENT_STATUS_UPDATE",
                "Department '" + department.getName() + "' status changed to " + newStatus
        );

        return mapToDto(department);
    }


}
