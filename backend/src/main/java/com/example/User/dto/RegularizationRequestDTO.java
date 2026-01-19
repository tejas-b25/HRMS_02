package com.example.User.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RegularizationRequestDTO {
    private Long employeeId;
    private LocalDate attendanceDate;
    private LocalDateTime requestedInTime;
    private LocalDateTime requestedOutTime;
    private String reason;
}