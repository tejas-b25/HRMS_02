package com.example.User.service;

import com.example.User.dto.HolidayFilter;
import com.example.User.dto.HolidayRequest;
import com.example.User.dto.HolidayResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HolidayService {
    HolidayResponse createHoliday(HolidayRequest request);
    HolidayResponse updateHoliday(Long id, HolidayRequest request);
    void deleteHoliday(Long id);
    HolidayResponse getHolidayById(Long id);
    List<HolidayResponse> listHolidays(HolidayFilter filter);
    List<HolidayResponse> getYearlyCalendar(int year, String region);
}
