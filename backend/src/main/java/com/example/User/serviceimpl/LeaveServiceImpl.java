package com.example.User.serviceimpl;

import com.example.User.dto.LeaveDTO;
import com.example.User.entity.Employee;
import com.example.User.entity.Leave;
import com.example.User.entity.LeaveStatus;
import com.example.User.entity.LeaveType;
import com.example.User.exception.BadRequestException;
import com.example.User.exception.LeaveNotFoundException;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.LeaveRepository;
import com.example.User.repository.LeaveTypeRepository;
import com.example.User.service.LeaveService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.nio.file.Path;


@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Leave applyLeave(LeaveDTO request, MultipartFile file) {
        List<Leave> overlapping = leaveRepository.findOverlappingLeaves(
                request.getEmployeeId(), request.getStartDate(), request.getEndDate()
        );
        if (!overlapping.isEmpty()) {
            throw new BadRequestException("Overlapping leave exists for the selected date range.");
        }
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + request.getEmployeeId()));

        LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave type not found"));

        int totalDays = (int) ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1;

        int available = getAvailableBalance(request.getEmployeeId(), leaveType.getLeaveTypeId());
        if (totalDays > available) {
            throw new BadRequestException("Insufficient leave balance. Requested: " + totalDays + ", Available: " + available);
        }
        String fileName = null;
        if (file != null && !file.isEmpty()) {
            try {
                String uploadDir = "uploads/leave_attachments/";
                File directory = new File(uploadDir);
                if (!directory.exists()) directory.mkdirs();

                fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("File upload failed", e);
            }
        }
        Leave leave = Leave.builder()
                .employeeId(request.getEmployeeId())
                .leaveType(leaveType)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .totalDays(totalDays)
                .reason(request.getReason())
                .status(LeaveStatus.PENDING)
                .attachment(fileName)
                .build();

        Leave saved = leaveRepository.save(leave);
        return leaveRepository.save(leave);
    }


    @Override
    public List<Leave> getLeavesByEmployee(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }


    @Override
    public Leave getLeaveById(Long leaveId) {
        return leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException("Leave not found with ID: " + leaveId));
    }


    @Override
    public Leave approveLeave(Long leaveId, Boolean approve) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException("Leave not found"));
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new BadRequestException("Only pending leaves can be approved or rejected.");
        }
        Employee employee = employeeRepository.findById(leave.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        if (approve) {
            if (employee.getReportingManager() != null) {
                leave.setApproverId(employee.getReportingManager().getId());
            } else {
                throw new BadRequestException("Employee does not have a reporting manager assigned.");
            }
            leave.setStatus(LeaveStatus.APPROVED);
        } else {
            leave.setStatus(LeaveStatus.REJECTED);
        }
        Leave saved = leaveRepository.save(leave);
        return leaveRepository.save(leave);
    }


    @Override
    public Leave cancelLeave(Long leaveId, Long employeeId) {
        Leave leave = getLeaveById(leaveId);

        if (!leave.getEmployeeId().equals(employeeId)) {
            throw new RuntimeException("You are not authorized to cancel this leave");
        }

        leave.setStatus(LeaveStatus.CANCELLED);
        return leaveRepository.save(leave);
    }

    @Override
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    private void validateDates(java.time.LocalDate start, java.time.LocalDate end) {
        if (end.isBefore(start)) {
            throw new BadRequestException("endDate must be same or after startDate");
        }
    }

    private int getAvailableBalance(@NotNull Long employeeId, Long leaveTypeId) {
        return 30;
    }
}
