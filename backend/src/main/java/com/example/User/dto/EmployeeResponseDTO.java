package com.example.User.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {
    private Long employeeId;
    private String username;
    private String firstName;
    private String lastName;
    private String name;        // full name
    private String email;
    private String contactNumber;

    private String designation;
    private String department;
    private String employmentType;
    private String status;

    private Long reportingManagerId;
    private String reportingManagerName;

    private Long hrbpId;
    private String hrbpName;
}
