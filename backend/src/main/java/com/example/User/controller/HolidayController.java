package com.example.User.controller;

import com.example.User.dto.HolidayFilter;
import com.example.User.dto.HolidayRequest;
import com.example.User.dto.HolidayResponse;
import com.example.User.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    @Autowired
    private HolidayService service;


    @PostMapping
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<HolidayResponse> create(@RequestBody HolidayRequest req) {
        return ResponseEntity.ok(service.createHoliday(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<HolidayResponse> update(@PathVariable Long id, @RequestBody HolidayRequest req) {
        return ResponseEntity.ok(service.updateHoliday(id, req));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<HolidayResponse>> list(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String region) {
        HolidayFilter f = new HolidayFilter();
        f.setYear(year != null ? year : java.time.LocalDate.now().getYear());
        f.setRegion(region);
        return ResponseEntity.ok(service.listHolidays(f));
    }
    @GetMapping("/{id}")
    public ResponseEntity<HolidayResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getHolidayById(id));
    }
    @GetMapping("/calendar")
    public ResponseEntity<List<HolidayResponse>> calendar(
            @RequestParam int year,
            @RequestParam(required = false) String region) {
        return ResponseEntity.ok(service.getYearlyCalendar(year, region));
    }

}
