package com.example.User.service;

import com.example.User.entity.LeaveType;
import java.util.List;

public interface LeaveTypeService {

    LeaveType createLeaveType(LeaveType leaveType);

    List<LeaveType> getAllLeaveTypes();

    LeaveType updateLeaveType(Long id, LeaveType leaveType);
    void deleteLeaveType(Long id);


}