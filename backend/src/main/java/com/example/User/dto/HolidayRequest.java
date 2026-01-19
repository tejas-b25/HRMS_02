package com.example.User.dto;

import com.example.User.entity.HolidayType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HolidayRequest {
    private String holidayName;
    private LocalDate holidayDate;
    private String region;
    private boolean optional;
    private HolidayType holidayType;
    private String description;
}
