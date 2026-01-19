package com.example.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "employee_payroll")
public class EmployeePayroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId;

    private Long employeeId;
    private String month;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private SalaryStructureMaster salaryStructure;

    private Double grossSalary;
    private Double totalDeductions;
    private Double netSalary;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDate paymentDate;
    private String bankReferenceNo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isActive = true;        // Added by Harshada

    private String createdBy;               // Added by Harshada
    private String updatedBy;               // Added by Harshada

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = "ADMIN";          // Added by Harshada
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "ADMIN";          // Added by Harshada
    }


    public enum PaymentStatus {
        PENDING,
        PROCESSED,
        FAILED
    }

}
