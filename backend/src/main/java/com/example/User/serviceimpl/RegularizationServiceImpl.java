package com.example.User.serviceimpl;

import com.example.User.dto.RegularizationRequestDTO;
import com.example.User.dto.RegularizationResponseDTO;
import com.example.User.entity.*;
import com.example.User.repository.AttendanceRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.RegularizationRepository;
import com.example.User.repository.UserRepository;
import com.example.User.service.RegularizationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RegularizationServiceImpl implements RegularizationService {

    @Autowired
    private RegularizationRepository regularizationRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RegularizationResponseDTO createRequest(RegularizationRequestDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate date = dto.getAttendanceDate();

        Attendance attendance = attendanceRepository.findByEmployeeAndAttendanceDate(employee, date)
                .orElseThrow(() -> new RuntimeException("Attendance not found for employee on " + date));

        // BUILD EMPLOYEE FULL NAME
        String empName = employee.getFirstName();
        if (employee.getLastName() != null && !employee.getLastName().isBlank()) {
            empName += " " + employee.getLastName();
        }

        AttendanceRegularization reg = AttendanceRegularization.builder()
                .employeeId(dto.getEmployeeId())
                .employeeName(empName)
                .attendanceId(attendance.getAttendanceId())
                .attendanceDate(date)
                .existingInTime(attendance.getClockInTime())
                .existingOutTime(attendance.getClockOutTime())
                .requestedInTime(dto.getRequestedInTime())
                .requestedOutTime(dto.getRequestedOutTime())
                .reason(dto.getReason())
                .status(RegularizationStatus.PENDING)
                .build();

        AttendanceRegularization saved = regularizationRepository.save(reg);
        return mapToResponse(saved);
    }

    @Override
    public RegularizationResponseDTO getById(Long id) {
        AttendanceRegularization reg = regularizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regularization not found"));
        return mapToResponse(reg);
    }

    @Override
    public List<RegularizationResponseDTO> getAll() {
        return regularizationRepository.findAll().stream()
                .map(this::mapToResponse).toList();
    }

    @Override
    public List<RegularizationResponseDTO> getByEmployeeId(Long employeeId) {
        return regularizationRepository.findAll().stream()
                .filter(r -> r.getEmployeeId().equals(employeeId))
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RegularizationResponseDTO approve(Long id, Long approverId) {
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));

        Role role = approver.getRole();
        if (!(role == Role.ADMIN || role == Role.HR || role == Role.MANAGER)) {
            throw new RuntimeException("You are not authorized to approve this request");
        }

        AttendanceRegularization reg = regularizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regularization not found"));

        reg.setStatus(RegularizationStatus.APPROVED);
        reg.setApproverId(approver.getId());

        String approverName = approver.getFirstName();
        if (approver.getLastName() != null && !approver.getLastName().isBlank()) {
            approverName += " " + approver.getLastName();
        }

        reg.setApproverName(approverName);
        reg.setApproverRole(role);

        AttendanceRegularization updated = regularizationRepository.save(reg);
        return mapToResponse(updated);
    }

    @Override
    public RegularizationResponseDTO reject(Long id, Long approverId) {
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));

        Role role = approver.getRole();
        if (!(role == Role.ADMIN || role == Role.HR || role == Role.MANAGER)) {
            throw new RuntimeException("You are not authorized to reject this request");
        }

        AttendanceRegularization reg = regularizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Regularization not found"));

        reg.setStatus(RegularizationStatus.REJECTED);
        reg.setApproverId(approver.getId());

        String approverName = approver.getFirstName();
        if (approver.getLastName() != null && !approver.getLastName().isBlank()) {
            approverName += " " + approver.getLastName();
        }

        reg.setApproverName(approverName);
        reg.setApproverRole(role);

        AttendanceRegularization updated = regularizationRepository.save(reg);
        return mapToResponse(updated);
    }

    private RegularizationResponseDTO mapToResponse(AttendanceRegularization reg) {
        return RegularizationResponseDTO.builder()
                .attendanceRegId(reg.getAttendanceRegId())
                .employeeId(reg.getEmployeeId())
                .employeeName(reg.getEmployeeName())
                .attendanceId(reg.getAttendanceId())
                .attendanceDate(reg.getAttendanceDate())
                .existingInTime(reg.getExistingInTime())
                .existingOutTime(reg.getExistingOutTime())
                .requestedInTime(reg.getRequestedInTime())
                .requestedOutTime(reg.getRequestedOutTime())
                .reason(reg.getReason())
                .status(reg.getStatus())
                .approverId(reg.getApproverId())
                .approverName(reg.getApproverName())
                .approverRole(reg.getApproverRole())
                .build();
    }
}