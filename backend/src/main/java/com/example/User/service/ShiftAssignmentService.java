package com.example.User.service;

import com.example.User.dto.ShiftAssignmentDTO;
import org.springframework.stereotype.Service;


public interface ShiftAssignmentService {
    ShiftAssignmentDTO createShiftAssignment(ShiftAssignmentDTO dto);
    ShiftAssignmentDTO getShiftByEmployeeId(Long employeeId);
}
