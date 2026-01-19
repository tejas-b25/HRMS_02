package com.example.User.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceReportRow {

    private Long attendanceId;
    private Long employeeId;
    private String employeeName;
    private LocalDate attendanceDate;
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    private String geoLocation;
    private String deviceType;
    private String status;
    private LocalDateTime createdAt;
    private String workingHours;
    private LocalDateTime updatedAt;

    public AttendanceReportRow(
            Long employeeId,
            String employeeName,
            LocalDate attendanceDate,
            LocalDateTime clockInTime,
            LocalDateTime clockOutTime,
            String status,
            String deviceType,
            String geoLocation,
            String workingHours,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.attendanceDate = attendanceDate;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.status = status;
        this.deviceType = deviceType;
        this.geoLocation = geoLocation;
        this.workingHours = workingHours;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}