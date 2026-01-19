package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kpi_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiMaster {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long kpiId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "departmentId",nullable = false)
    private Department department;
    @Enumerated(EnumType.STRING)
    private Role role;

    private String kpiName;

    private String kpiDescription;

    private Double weightage;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
