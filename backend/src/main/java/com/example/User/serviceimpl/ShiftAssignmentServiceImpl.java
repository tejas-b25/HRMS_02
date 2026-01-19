package com.example.User.serviceimpl;

import com.example.User.dto.ShiftAssignmentDTO;
import com.example.User.entity.Employee;
import com.example.User.entity.ShiftAssignment;
import com.example.User.entity.ShiftMaster;
import com.example.User.exception.BadRequestException;
import com.example.User.exception.ConflictException;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.ShiftAssignmentRepository;
import com.example.User.repository.ShiftMasterRepository;
import com.example.User.service.ShiftAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShiftAssignmentServiceImpl implements ShiftAssignmentService {

    @Autowired
    private ShiftAssignmentRepository shiftAssignmentRepository;

    @Autowired
    private ShiftMasterRepository shiftMasterRepository;
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private  ShiftAssignmentRepository assignmentRepo;

    // Create Shift Assignment
    @Override
    public ShiftAssignmentDTO createShiftAssignment(ShiftAssignmentDTO dto) {
        LocalDate today = LocalDate.now();
        if (dto.getEffectiveFromDate().isAfter(dto.getEffectiveToDate())) {
            throw new BadRequestException("effectiveFromDate must be <= effectiveToDate");
        }
        if (dto.getEffectiveFromDate().isBefore(today)) {
            throw new BadRequestException("effectiveFromDate cannot be in the past");
        }
        Employee employee = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new BadRequestException("Employee not found with ID: " + dto.getEmployeeId()));
        ShiftMaster shift = shiftMasterRepository.findById(dto.getShiftId())
                .orElseThrow(() -> new BadRequestException("Shift not found"));
        List<ShiftAssignment> overlapping = assignmentRepo.findOverlappingAssignments(
                dto.getEmployeeId(), dto.getEffectiveFromDate(), dto.getEffectiveToDate());
        if (!overlapping.isEmpty()) {
            throw new ConflictException("Employee already has a shift assigned in the given date range");
        }
        ShiftAssignment entity = ShiftAssignment.builder()
                .employeeId(dto.getEmployeeId())
                .shift(shift)
                .effectiveFromDate(dto.getEffectiveFromDate())
                .effectiveToDate(dto.getEffectiveToDate())
                .assignedBy(dto.getAssignedBy())
                .isActive(true)
                .createdBy("Admin")
                .updatedBy("Admin")
                .assignedBy("Hr")
                .build();
        ShiftAssignment saved = assignmentRepo.save(entity);
        return mapToDTO(saved);
    }
    // Helper Method
    private ShiftAssignmentDTO mapToDTO(ShiftAssignment assignment) {
        return ShiftAssignmentDTO.builder()
                .shiftAssignmentId(assignment.getShiftAssignmentId())
                .employeeId(assignment.getEmployeeId())
                .shiftId(assignment.getShift().getShiftId())
                .effectiveFromDate(assignment.getEffectiveFromDate())
                .effectiveToDate(assignment.getEffectiveToDate())
                .assignedBy(assignment.getAssignedBy())
                .isActive(assignment.getIsActive())
                .build();
    }

    ///

    @Override
    public ShiftAssignmentDTO getShiftByEmployeeId(Long employeeId) {
        // Find latest (or active) shift for employee
        ShiftAssignment shiftAssignment = shiftAssignmentRepository
                .findTopByEmployeeIdAndIsActiveTrueOrderByEffectiveFromDateDesc(employeeId)
                .orElseThrow(() -> new RuntimeException("No active shift found for employee ID: " + employeeId));
        // Map entity → DTO
        ShiftAssignmentDTO dto = new ShiftAssignmentDTO();
        dto.setShiftAssignmentId(shiftAssignment.getShiftAssignmentId());
        dto.setEmployeeId(shiftAssignment.getEmployeeId());
        dto.setShiftId(shiftAssignment.getShift().getShiftId());
        dto.setEffectiveFromDate(shiftAssignment.getEffectiveFromDate());
        dto.setEffectiveToDate(shiftAssignment.getEffectiveToDate());
        dto.setAssignedBy(shiftAssignment.getAssignedBy());
        dto.setIsActive(shiftAssignment.getIsActive());

        return dto;
    }
}
