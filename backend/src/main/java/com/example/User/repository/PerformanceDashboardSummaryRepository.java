package com.example.User.repository;

import com.example.User.entity.PerformanceDashboardSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceDashboardSummaryRepository extends JpaRepository<PerformanceDashboardSummary, Long> {

    List<PerformanceDashboardSummary> findByDepartmentName(String departmentName);
    List<PerformanceDashboardSummary> findByReviewPeriod(String reviewPeriod);
}
