package com.example.User.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceReviewDto {
    private Long reviewId;
    private Long employeeId;
    private Long kpiId;
    private String reviewPeriod;
    private BigDecimal selfScore;
    private BigDecimal managerScore;
    private BigDecimal finalScore;
    private String feedback;
    private String reviewStatus;
}
