package com.example.User.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceDashboardSummaryDTO {
    private Long dashboardId;
    private String departmentName;
    private Double avgScore;
    private Integer totalEmployees;
    private Double topScore;
    private Double bottomScore;
    private String reviewPeriod;
    private LocalDateTime generatedAt;
}
