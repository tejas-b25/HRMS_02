package com.example.User.controller;

import com.example.User.dto.DepartmentCreateDto;
import com.example.User.dto.DepartmentResponseDto;
import com.example.User.dto.DepartmentUpdateDto;
import com.example.User.entity.DepartmentStatus;
import com.example.User.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Create Department
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    @PostMapping
    public ResponseEntity<DepartmentResponseDto> create(@Valid @RequestBody DepartmentCreateDto dto) {
        DepartmentResponseDto created = departmentService.createDepartment(dto);
        return ResponseEntity.ok(created);
    }

    // Update Department

    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(
            @PathVariable("id") Long id,
            @Valid @RequestBody DepartmentUpdateDto dto) {

        DepartmentResponseDto updated = departmentService.updateDepartment(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Get by id for Department
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getById(@PathVariable("id") Long departmentId) {
        DepartmentResponseDto dto = departmentService.getDepartmentById(departmentId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    // List / search / filter (pagination)
    @GetMapping
    public ResponseEntity<Page<DepartmentResponseDto>> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "status", required = false) String status) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DepartmentResponseDto> result = departmentService.listDepartments(pageable, status);
        return ResponseEntity.ok(result);
    }

    // Soft delete (mark inactive)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('HR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        departmentService.softDeleteDepartment(id);
        return ResponseEntity.ok("Department deactivated successfully");
    }

//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or hasRole('USER')")
//    public ResponseEntity<DepartmentResponseDto> delete(@PathVariable("id") Long id) {
//        DepartmentResponseDto updated = departmentService.softDeleteDepartment(id);
//        return ResponseEntity.ok(updated);
//    }


    // Added new
    // Search Departments by name or code
    @GetMapping("/search")
    public ResponseEntity<List<DepartmentResponseDto>> searchDepartments(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) DepartmentStatus status) {

        if (query != null && status != null) {
            // Search + Filter both
            return ResponseEntity.ok(departmentService.searchDepartmentsByStatus(query, status));
        } else if (query != null) {
            // Only Search
            return ResponseEntity.ok(departmentService.searchDepartments(query));
        } else if (status != null) {
            // Only Filter
            return ResponseEntity.ok(departmentService.filterDepartmentsByStatus(status));
        } else {
            return ResponseEntity.badRequest().build();
        }


    }


}
