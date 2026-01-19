package com.example.User.service;

import com.example.User.dto.LeaveDTO;
import com.example.User.entity.Leave;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LeaveService {
    Leave applyLeave(LeaveDTO request, MultipartFile file);
    List<Leave> getLeavesByEmployee(Long employeeId);
    Leave getLeaveById(Long leaveId);
    Leave approveLeave(Long leaveId, Boolean approve);
    Leave cancelLeave(Long leaveId, Long employeeId);
    List<Leave> getAllLeaves();
}
