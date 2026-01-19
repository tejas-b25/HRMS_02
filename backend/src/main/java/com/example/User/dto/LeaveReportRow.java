package com.example.User.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class LeaveReportRow {

    private Long leaveId;
    private Long employeeId;
    private String employeeName;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalDays;
    private String reason;
    private String status;
    private LocalDateTime appliedDate;
    private LocalDateTime approvedDate;
    private String approverName;
    private String approverRole;

    public LeaveReportRow(Long leaveId,
                          Long employeeId,
                          String employeeName,
                          String leaveType,
                          LocalDate startDate,
                          LocalDate endDate,
                          Integer totalDays,
                          String reason,
                          String status,
                          LocalDateTime appliedDate,
                          LocalDateTime approvedDate,
                          String approverName,
                          String approverRole) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.reason = reason;
        this.status = status;
        this.appliedDate = appliedDate;
        this.approvedDate = approvedDate;
        this.approverName = approverName;
        this.approverRole = approverRole;
    }
}