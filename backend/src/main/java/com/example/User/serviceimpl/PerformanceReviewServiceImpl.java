package com.example.User.serviceimpl;

import com.example.User.dto.PerformanceReviewDto;
import com.example.User.entity.Employee;
import com.example.User.entity.KpiMaster;
import com.example.User.entity.PerformanceReview;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.KpiMasterRepository;
import com.example.User.repository.PerformanceReviewRepository;
import com.example.User.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    @Autowired
    private  PerformanceReviewRepository reviewRepo;
    @Autowired
    private  EmployeeRepository employeeRepo;
    @Autowired
    private  KpiMasterRepository kpiRepo;

    @Override
    public PerformanceReviewDto createReview(PerformanceReviewDto dto) {
        // Validation: Only one active review per employee + period
        if (reviewRepo.existsByEmployee_EmployeeIdAndReviewPeriod(dto.getEmployeeId(), dto.getReviewPeriod())) {
            throw new IllegalStateException("Review already exists for this employee and period.");
        }

        Employee employee = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        KpiMaster kpi = kpiRepo.findById(dto.getKpiId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid KPI ID"));

        PerformanceReview review = PerformanceReview.builder()
                .employee(employee)
                .kpi(kpi)
                .reviewPeriod(dto.getReviewPeriod())
                .selfScore(dto.getSelfScore())
                .managerScore(dto.getManagerScore())
                .feedback(dto.getFeedback())
                .reviewStatus(PerformanceReview.ReviewStatus.valueOf(
                        dto.getReviewStatus() != null ? dto.getReviewStatus() : "Pending"))
                .finalScore(calculateFinalScore(dto.getSelfScore(), dto.getManagerScore()))
                .build();

        PerformanceReview saved = reviewRepo.save(review);
        return mapToDto(saved);
    }

    @Override
    public PerformanceReviewDto updateReview(Long reviewId, PerformanceReviewDto dto) {
        PerformanceReview review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        if (dto.getSelfScore() != null) review.setSelfScore(dto.getSelfScore());
        if (dto.getManagerScore() != null) review.setManagerScore(dto.getManagerScore());
        if (dto.getFeedback() != null) review.setFeedback(dto.getFeedback());
        if (dto.getReviewStatus() != null)
            review.setReviewStatus(PerformanceReview.ReviewStatus.valueOf(dto.getReviewStatus()));

        review.setFinalScore(calculateFinalScore(review.getSelfScore(), review.getManagerScore()));
        PerformanceReview updated = reviewRepo.save(review);

        return mapToDto(updated);
    }

    @Override
    public PerformanceReviewDto getReviewById(Long reviewId) {
        PerformanceReview review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return mapToDto(review);
    }

    @Override
    public List<PerformanceReviewDto> getReviewsByEmployee(Long employeeId) {
        return reviewRepo.findByEmployee_EmployeeId(employeeId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepo.deleteById(reviewId);
    }

    // Utility: calculate average final score
    private BigDecimal calculateFinalScore(BigDecimal self, BigDecimal manager) {
        BigDecimal selfSafe = self != null ? self : BigDecimal.ZERO;
        BigDecimal mgrSafe = manager != null ? manager : BigDecimal.ZERO;
        return selfSafe.add(mgrSafe)
                .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
    }

    // Mapper method
    private PerformanceReviewDto mapToDto(PerformanceReview review) {
        return PerformanceReviewDto.builder()
                .reviewId(review.getReviewId())
                .employeeId(review.getEmployee().getEmployeeId())
                .kpiId(review.getKpi().getKpiId())
                .reviewPeriod(review.getReviewPeriod())
                .selfScore(review.getSelfScore())
                .managerScore(review.getManagerScore())
                .finalScore(review.getFinalScore())
                .feedback(review.getFeedback())
                .reviewStatus(review.getReviewStatus().name())
                .build();
    }
}
