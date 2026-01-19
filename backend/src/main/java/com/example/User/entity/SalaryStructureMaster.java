package com.example.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salary_structure_master")
public class SalaryStructureMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long structureId;

    @Column(nullable = false, unique = true, length = 100)
    private String structureName;

    private Double basicPay;
    private Double hra;
    private Double variablePay;
    private Double otherAllowances;
    private Double deductions;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String createdBy;   // Added by Harshada
    private String updatedBy;   // Added by Harshada

    @Column(nullable = false)
    private Boolean isActive = true;   // Added by Harshada

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = "ADMIN";     // Added by Harshada
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "ADMIN";      // Added by Harshada
    }
}
