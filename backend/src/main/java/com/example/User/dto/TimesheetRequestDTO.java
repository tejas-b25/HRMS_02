package com.example.User.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimesheetRequestDTO {
    @NotNull(message = "employeeId is mandatory")
    private Long employeeId;

    @NotBlank(message = "projectCode is mandatory")
    private String projectCode;

    @NotBlank(message = "taskCode is mandatory")
    private String taskCode;

    @NotNull(message = "workDate is mandatory")
    private LocalDate workDate;

    @NotNull(message = "hoursWorked is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "hoursWorked must be > 0")
    private Double hoursWorked;

    private String remarks;


}
