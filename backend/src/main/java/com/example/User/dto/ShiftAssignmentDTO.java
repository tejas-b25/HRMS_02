package com.example.User.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftAssignmentDTO {
    private Long shiftAssignmentId;

    @NotNull
    private Long employeeId;

    @NotNull
    private Long shiftId;

    @NotNull
    private LocalDate effectiveFromDate;

    @NotNull
    private LocalDate effectiveToDate;

    private String assignedBy;

    //private String shiftName;
    //private LocalTime startTime;
    //private LocalTime endTime;
    //private Integer breakMinutes;
    //private Integer graceMinutes;
    //private Boolean isRotational;
    //private String weekOff;
    private Boolean isActive;


}
