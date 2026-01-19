package com.example.User.service;

import com.example.User.dto.PerformanceDashboardSummaryDTO;

import java.util.List;

public interface PerformanceDashboardSummaryService {
    PerformanceDashboardSummaryDTO createDashboardSummary(PerformanceDashboardSummaryDTO dto);
    PerformanceDashboardSummaryDTO getDashboardById(Long id);
    List<PerformanceDashboardSummaryDTO> getAllDashboards();
    List<PerformanceDashboardSummaryDTO> getDashboardByDepartment(String departmentName);
    List<PerformanceDashboardSummaryDTO> getDashboardByReviewPeriod(String reviewPeriod);
    void deleteDashboard(Long id);
}
