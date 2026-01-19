package com.example.User.service;

import com.example.User.dto.RegularizationRequestDTO;
import com.example.User.dto.RegularizationResponseDTO;

import java.util.List;

public interface RegularizationService {
    RegularizationResponseDTO createRequest(RegularizationRequestDTO dto);
    RegularizationResponseDTO getById(Long id);
    List<RegularizationResponseDTO> getAll();
    List<RegularizationResponseDTO> getByEmployeeId(Long employeeId);
    RegularizationResponseDTO approve(Long id, Long approverId);
    RegularizationResponseDTO reject(Long id, Long approverId);
}
