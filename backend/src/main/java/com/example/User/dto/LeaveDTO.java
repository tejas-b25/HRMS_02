package com.example.User.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveDTO {
    private Long employeeId;
    private Long leaveTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String attachment;

}
