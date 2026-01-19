package com.example.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "attendance_regularization")
public class AttendanceRegularization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_reg_id")
    private Long attendanceRegId;

    @Column(nullable = false)
    private Long employeeId;

    private Long attendanceId;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    private LocalDateTime existingInTime;
    private LocalDateTime existingOutTime;

    private LocalDateTime requestedInTime;
    private LocalDateTime requestedOutTime;

    private String reason;

    @Enumerated(EnumType.STRING)
    private RegularizationStatus status;

    private Long approverId;
    private String approverName;

    @Enumerated(EnumType.STRING)
    private Role approverRole;

    private String employeeName;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
