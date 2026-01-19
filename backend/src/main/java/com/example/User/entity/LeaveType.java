package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveTypeId;

    private String name;
    private String description;
    private Integer annualLimit;
    private Boolean carryForward;
    private Boolean encashable;
    private Boolean isActive;
}