package com.example.User.dto;

import com.example.User.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiMasterDto {
    private Long kpiId;
    private Long departmentId;
    private Role role;
    private String kpiName;
    private String kpiDescription;
    private Double weightage;
}