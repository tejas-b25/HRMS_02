package com.example.User.dto;

import lombok.Data;

@Data
public class LeaveTypeDTO {

    private Long leaveTypeId;
    private String name;
    private String description;
    private Integer annualLimit;
    private Boolean carryForward;
    private Boolean encashable;
    private Boolean isActive;
}