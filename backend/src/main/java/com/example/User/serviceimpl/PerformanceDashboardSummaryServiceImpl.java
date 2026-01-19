package com.example.User.serviceimpl;

import com.example.User.dto.PerformanceDashboardSummaryDTO;
import com.example.User.entity.PerformanceDashboardSummary;
import com.example.User.repository.PerformanceDashboardSummaryRepository;
import com.example.User.service.PerformanceDashboardSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceDashboardSummaryServiceImpl  implements PerformanceDashboardSummaryService {
    @Autowired
    private PerformanceDashboardSummaryRepository repository;
    @Override
    public PerformanceDashboardSummaryDTO createDashboardSummary(PerformanceDashboardSummaryDTO dto) {
        PerformanceDashboardSummary entity = convertToEntity(dto);
        PerformanceDashboardSummary saved = repository.save(entity);
        return convertToDTO(saved);
    }

    @Override
    public PerformanceDashboardSummaryDTO getDashboardById(Long id) {
        PerformanceDashboardSummary entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dashboard not found with ID: " + id));
        return convertToDTO(entity);
    }

    @Override
    public List<PerformanceDashboardSummaryDTO> getAllDashboards() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PerformanceDashboardSummaryDTO> getDashboardByDepartment(String departmentName) {
        return repository.findByDepartmentName(departmentName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PerformanceDashboardSummaryDTO> getDashboardByReviewPeriod(String reviewPeriod) {
        return repository.findByReviewPeriod(reviewPeriod)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDashboard(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Dashboard not found with ID: " + id);
        }
        repository.deleteById(id);
    }
    private PerformanceDashboardSummary convertToEntity(PerformanceDashboardSummaryDTO dto) {
        return PerformanceDashboardSummary.builder()
                .dashboardId(dto.getDashboardId())
                .departmentName(dto.getDepartmentName())
                .avgScore(dto.getAvgScore())
                .totalEmployees(dto.getTotalEmployees())
                .topScore(dto.getTopScore())
                .bottomScore(dto.getBottomScore())
                .reviewPeriod(dto.getReviewPeriod())
                .generatedAt(dto.getGeneratedAt())
                .build();
    }
    private PerformanceDashboardSummaryDTO convertToDTO(PerformanceDashboardSummary entity) {
        return PerformanceDashboardSummaryDTO.builder()
                .dashboardId(entity.getDashboardId())
                .departmentName(entity.getDepartmentName())
                .avgScore(entity.getAvgScore())
                .totalEmployees(entity.getTotalEmployees())
                .topScore(entity.getTopScore())
                .bottomScore(entity.getBottomScore())
                .reviewPeriod(entity.getReviewPeriod())
                .generatedAt(entity.getGeneratedAt())
                .build();
    }
}
