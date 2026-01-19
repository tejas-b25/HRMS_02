package com.example.User.serviceimpl;


import com.example.User.dto.ShiftMasterDTO;
import com.example.User.entity.ShiftMaster;
import com.example.User.repository.ShiftMasterRepository;
import com.example.User.service.ShiftMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftMasterServiceImpl implements ShiftMasterService {

    @Autowired
    private ShiftMasterRepository shiftMasterRepository;

    @Override
    public ShiftMasterDTO createShift(ShiftMasterDTO shiftDTO) {

        if (shiftDTO.getShiftName() == null || shiftDTO.getShiftName().isEmpty()) {
            throw new RuntimeException("Shift name is required");
        }
        if (shiftDTO.getStartTime() == null || shiftDTO.getEndTime() == null) {
            throw new RuntimeException("Start time and end time are required");
        }
        if (shiftMasterRepository.findByShiftName(shiftDTO.getShiftName()).isPresent()) {
            throw new RuntimeException("Shift name already exists");
        }
        ShiftMaster shift = new ShiftMaster();
        shift.setShiftName(shiftDTO.getShiftName());
        shift.setStartTime(shiftDTO.getStartTime());
        shift.setEndTime(shiftDTO.getEndTime());
        shift.setWeekOff(shiftDTO.getWeekOff());
        shift.setGraceMinutes(shiftDTO.getGraceMinutes());
        shift.setBreakMinutes(shiftDTO.getBreakMinutes());
        shift.setCreatedBy("admin");
        shift.setUpdatedBy("admin");
        shift.setCreatedAt(LocalDateTime.now());
        shift.setUpdatedAt(LocalDateTime.now());
        ShiftMaster savedShift = shiftMasterRepository.save(shift);
        ShiftMasterDTO response = new ShiftMasterDTO();
        response.setShiftName(savedShift.getShiftName());
        response.setStartTime(savedShift.getStartTime());
        response.setEndTime(savedShift.getEndTime());
        response.setWeekOff(savedShift.getWeekOff());
        response.setGraceMinutes(savedShift.getGraceMinutes());
        response.setBreakMinutes(savedShift.getBreakMinutes());
        response.setCreatedBy(savedShift.getShiftName());
        response.setUpdatedBy(savedShift.getUpdatedBy());
        response.setIsOvernight(savedShift.getIsOvernight());
        return response;
    }

    ////

    @Override
    public ShiftMasterDTO getShiftById(Long shiftId) {
        ShiftMaster shift = shiftMasterRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));
        return mapToDTO(shift);
    }
    private ShiftMasterDTO mapToDTO(ShiftMaster entity) {
        return ShiftMasterDTO.builder()
                .shiftId(entity.getShiftId())
                .shiftName(entity.getShiftName())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .weekOff(entity.getWeekOff())
                .graceMinutes(entity.getGraceMinutes())
                .breakMinutes(entity.getBreakMinutes())
                .isRotational(entity.getIsRotational())
                .isActive(entity.getIsActive())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .isOvernight(entity.getIsOvernight())
                .isOvernight(Boolean.valueOf(entity.getIsOvernight() ? "Yes" : "No"))
                .build();
    }

    ///
    @Override
    public List<ShiftMasterDTO> getAllShifts() {
        return shiftMasterRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    ///
    @Override
    public void deleteShift(Long shiftId) {
        ShiftMaster shift = shiftMasterRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));
        shiftMasterRepository.delete(shift);
    }

    ///
    @Override
    public ShiftMasterDTO updateShift(Long shiftId, ShiftMasterDTO shiftDTO) {
        ShiftMaster shift = shiftMasterRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found with ID: " + shiftId));
        shift.setShiftName(shiftDTO.getShiftName());
        shift.setStartTime(shiftDTO.getStartTime());
        shift.setEndTime(shiftDTO.getEndTime());
        shift.setWeekOff(shiftDTO.getWeekOff());
        shift.setGraceMinutes(shiftDTO.getGraceMinutes());
        shift.setBreakMinutes(shiftDTO.getBreakMinutes());
        shift.setIsRotational(shiftDTO.getIsRotational());
        shift.setUpdatedBy("admin");
        shift.setUpdatedAt(LocalDateTime.now());

        ShiftMaster updated = shiftMasterRepository.save(shift);
        return mapToDTO(updated);
    }




}
