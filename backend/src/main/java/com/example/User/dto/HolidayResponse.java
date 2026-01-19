package com.example.User.dto;

import com.example.User.entity.HolidayType;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class HolidayResponse {
    private Long holidayId;
    private String holidayName;
    private LocalDate holidayDate;
    private String region;
    private boolean optional;
    private HolidayType holidayType;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
