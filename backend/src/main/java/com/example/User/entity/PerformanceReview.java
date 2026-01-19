package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "performance_review") @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kpi_id", nullable = false)
    private KpiMaster kpi;
    @Column(name = "review_period", length = 50, nullable = false)
    private String reviewPeriod;
    @Column(name = "self_score", precision = 5, scale = 2)
    private BigDecimal selfScore;
    @Column(name = "manager_score", precision = 5, scale = 2)
    private BigDecimal managerScore;
    @Column(name = "final_score", precision = 5, scale = 2)
    private BigDecimal finalScore;
    @Column(columnDefinition = "TEXT")
    private String feedback;
    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (reviewStatus == null) reviewStatus = ReviewStatus.Pending;
    }
    @PreUpdate public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    public enum ReviewStatus { Pending, Completed }
}
