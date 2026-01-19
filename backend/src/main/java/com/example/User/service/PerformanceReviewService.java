package com.example.User.service;

import com.example.User.dto.PerformanceReviewDto;

import java.util.List;

public interface PerformanceReviewService {
    PerformanceReviewDto createReview(PerformanceReviewDto dto);
    PerformanceReviewDto updateReview(Long reviewId, PerformanceReviewDto dto);
    PerformanceReviewDto getReviewById(Long reviewId);
    List<PerformanceReviewDto> getReviewsByEmployee(Long employeeId);
    void deleteReview(Long reviewId);
}
