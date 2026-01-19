package com.example.User.service;


import com.example.User.dto.ShiftMasterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShiftMasterService {
    ShiftMasterDTO createShift(ShiftMasterDTO shiftDTO);
    ShiftMasterDTO getShiftById(Long shiftId);
    List<ShiftMasterDTO> getAllShifts();
    void deleteShift(Long shiftId);
    ShiftMasterDTO updateShift(Long shiftId, ShiftMasterDTO shiftDTO);

}
