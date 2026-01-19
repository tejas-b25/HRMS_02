package com.example.User.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeBankDetailsResponseDto {

    private Long employeeBankDetailsId;
    private Long employeeId;
    private String bankName;
    private String accountHolderName;
    private String accountType;
    private String accountNumber;
    private String ifsc;
    private String branch;
    private String uanNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String employeeName;
    private String employeeEmail;
    private String department;

}

