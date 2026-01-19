package com.example.User.serviceimpl;

import com.example.User.entity.LeaveType;
import com.example.User.exception.LeaveTypeNotFoundException;
import com.example.User.repository.LeaveTypeRepository;
import com.example.User.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveType createLeaveType(LeaveType leaveType) {
        // Check for duplicate leave type name
        if (leaveTypeRepository.existsByName(leaveType.getName())) {
            throw new IllegalArgumentException("Leave type already exists: " + leaveType.getName());
        }
        leaveType.setIsActive(true);
        return leaveTypeRepository.save(leaveType);
    }

    @Override
    public List<LeaveType> getAllLeaveTypes() {
        return leaveTypeRepository.findAll();
    }

    @Override
    public LeaveType updateLeaveType(Long id, LeaveType leaveType) {
        LeaveType existing = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new LeaveTypeNotFoundException("Leave Type not found with ID: " + id));
        existing.setName(leaveType.getName());
        existing.setDescription(leaveType.getDescription());
        existing.setAnnualLimit(leaveType.getAnnualLimit());
        existing.setCarryForward(leaveType.getCarryForward());
        existing.setEncashable(leaveType.getEncashable());
        existing.setIsActive(leaveType.getIsActive());
        return leaveTypeRepository.save(existing);
    }

    @Override
    public void deleteLeaveType(Long id) {
        if (!leaveTypeRepository.existsById(id)) {
            throw new LeaveTypeNotFoundException("Leave Type not found with ID: " + id);
        }
        leaveTypeRepository.deleteById(id);
    }







}