package com.example.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_compliance_mapping",
        uniqueConstraints = @UniqueConstraint(columnNames = {"employeeId", "complianceId", "status"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeComplianceMapping {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid2")
    private Long complianceMappingId;

    private Long employeeId;
    private Long complianceId;
    private String complianceNumber;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String remarks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void onCreate() { this.createdAt = LocalDateTime.now(); }

    @PreUpdate
    public void onUpdate() { this.updatedAt = LocalDateTime.now(); }

    public enum Status { Active, Inactive }
}
