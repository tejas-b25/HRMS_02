package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "performance_dashboard_summary") @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceDashboardSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dashboardId;

    @Column(length = 100)
    private String departmentName;
    private Double avgScore;
    private Integer totalEmployees;
    private Double topScore;
    private Double bottomScore;
    @Column(length = 50)
    private String reviewPeriod;
    private LocalDateTime generatedAt;
    @PrePersist public void prePersist(){ generatedAt = LocalDateTime.now(); }
}
