package com.example.User.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
    @Table(name = "timesheets")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Timesheet {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "timesheet_id", nullable = false, updatable = false)
        private Long timesheetId;

//        @Column(name = "employee_id", nullable = false)
//        private Long employeeId; // FK to Employee (enforced at app level)

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

        @Column(name = "project_code", nullable = false, length = 100)
        private String projectCode;

        @Column(name = "task_code", nullable = false, length = 100)
        private String taskCode;

        @Column(name = "work_date", nullable = false)
        private LocalDate workDate;

        @Column(name = "hours_worked", nullable = false)
        private Double hoursWorked;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false, length = 20)
        private TimesheetStatus status;

        @Column(name = "approved_by")
        private Long approvedBy; // map with employee entity manager id

        @Column(name = "remarks", length = 1000)
        private String remarks;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;




    }
