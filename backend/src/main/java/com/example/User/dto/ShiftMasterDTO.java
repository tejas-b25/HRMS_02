package com.example.User.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ShiftMasterDTO {

    private Long shiftId;

    @NotBlank(message = "Shift name is mandatory")
    private String shiftName;

    @NotNull(message = "Start time is mandatory")
    private LocalTime startTime;

    @NotNull(message = "End time is mandatory")
    private LocalTime endTime;

    private String weekOff; // e.g., "Saturday,Sunday"

    private Boolean isActive = true;

    private Integer graceMinutes = 0;

    private Integer breakMinutes = 0;

    private Boolean isRotational = false;

    private String createdBy;
    private String updatedBy;

    private Boolean isOvernight;

    // Getters and Setters
}



