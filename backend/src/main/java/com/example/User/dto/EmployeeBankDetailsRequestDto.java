package com.example.User.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBankDetailsRequestDto {

    private Long employeeId;
    private String bankName;
    private String accountHolderName;
    private String accountNumber;
    private String accountType;
    private String ifsc;
    private String branch;
    private String uanNumber;
}

