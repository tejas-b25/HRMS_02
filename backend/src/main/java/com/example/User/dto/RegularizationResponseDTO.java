package com.example.User.dto;

import com.example.User.entity.RegularizationStatus;
import com.example.User.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class RegularizationResponseDTO {
    private Long attendanceRegId;
    private Long employeeId;
    private String employeeName;
    private Long attendanceId;
    private LocalDate attendanceDate;
    private LocalDateTime existingInTime;
    private LocalDateTime existingOutTime;
    private LocalDateTime requestedInTime;
    private LocalDateTime requestedOutTime;
    private String reason;
    private RegularizationStatus status;
    private Long approverId;
    private String approverName;
    private Role approverRole;
}
