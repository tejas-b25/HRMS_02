package com.example.User.service;

import com.example.User.dto.DepartmentCreateDto;
import com.example.User.dto.DepartmentResponseDto;
import com.example.User.dto.DepartmentUpdateDto;
import com.example.User.entity.DepartmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {

    DepartmentResponseDto createDepartment(DepartmentCreateDto dto);

    DepartmentResponseDto updateDepartment(Long id, DepartmentUpdateDto dto);

    DepartmentResponseDto getDepartmentById(Long departmentId);

    Page<DepartmentResponseDto> listDepartments(Pageable pageable, String statusFilter);

    void softDeleteDepartment(Long id); // mark INACTIVE

 // Added new functions
    List<DepartmentResponseDto> searchDepartments(String query);

    List<DepartmentResponseDto> filterDepartmentsByStatus(DepartmentStatus status);

    List<DepartmentResponseDto> searchDepartmentsByStatus(String query, DepartmentStatus status);

    DepartmentResponseDto updateDepartmentStatus(Long id, String status, String performedBy);
}
