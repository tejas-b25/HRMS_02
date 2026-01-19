package com.example.User.serviceimpl;

import com.example.User.dto.HolidayFilter;
import com.example.User.dto.HolidayRequest;
import com.example.User.dto.HolidayResponse;
import com.example.User.entity.HolidayMaster;
import com.example.User.exception.BadRequestException;
import com.example.User.exception.DuplicateHolidayException;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.HolidayRepository;
import com.example.User.service.HolidayService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    private HolidayRepository repo;

    // this us used to get the currently login users using username
    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.getName() != null) ? auth.getName() : "system";
    }
        // using for validation
    private void validate(HolidayRequest req) {
        if (req.getHolidayName() == null || req.getHolidayName().isBlank())
            throw new BadRequestException("holidayName is required");
        if (req.getHolidayDate() == null)
            throw new BadRequestException("holidayDate is required");
        if (req.getHolidayType() == null)
            throw new BadRequestException("holidayType is required");
    }
    //This method prevents duplicate holidays (same date + region) when creating or updating a record.
    private void checkDuplicate(LocalDate date, String region, Long excludeId) {
        boolean exists = repo.existsByHolidayDateAndRegionAndIdNot(date, region, excludeId);
        if (exists)
            throw new DuplicateHolidayException("Duplicate holiday for same date and region");
    }
    private HolidayResponse map(HolidayMaster h) {
        HolidayResponse r = new HolidayResponse();
        r.setHolidayId(h.getHolidayId());
        r.setHolidayName(h.getHolidayName());
        r.setHolidayDate(h.getHolidayDate());
        r.setRegion(h.getRegion());
        r.setOptional(h.isOptional());
        r.setHolidayType(h.getHolidayType());
        r.setDescription(h.getDescription());
        r.setCreatedAt(h.getCreatedAt());
        r.setUpdatedAt(h.getUpdatedAt());
        r.setCreatedBy(h.getCreatedBy());
        r.setUpdatedBy(h.getUpdatedBy());
        return r;
    }

    @Override
    @Transactional
    public HolidayResponse createHoliday(HolidayRequest request) {
        validate(request);
        checkDuplicate(request.getHolidayDate(), request.getRegion(), null);

        HolidayMaster h = HolidayMaster.builder()
                .holidayName(request.getHolidayName())
                .holidayDate(request.getHolidayDate())
                .region(request.getRegion())
                .isOptional(request.isOptional())
                .holidayType(request.getHolidayType())
                .description(request.getDescription())
                .createdBy(getUsername())
                .updatedBy(getUsername())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        return map(repo.save(h));
    }

    @Override
    public HolidayResponse updateHoliday(Long id, HolidayRequest request) {
        HolidayMaster h = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found"));

        checkDuplicate(request.getHolidayDate(), request.getRegion(), id);

        if (request.getHolidayName() != null) h.setHolidayName(request.getHolidayName());
        if (request.getHolidayDate() != null) h.setHolidayDate(request.getHolidayDate());
        if (request.getRegion() != null) h.setRegion(request.getRegion());
        h.setOptional(request.isOptional());
        if (request.getHolidayType() != null) h.setHolidayType(request.getHolidayType());
        if (request.getDescription() != null) h.setDescription(request.getDescription());
        h.setUpdatedBy(getUsername());

        return map(repo.save(h));
    }

    @Override
    public void deleteHoliday(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<HolidayResponse> listHolidays(HolidayFilter filter) {
        //this define entire range of that year
        LocalDate start = LocalDate.of(filter.getYear(), 1, 1);
        LocalDate end = LocalDate.of(filter.getYear(), 12, 31);
        return repo.findByDateRangeAndRegion(start, end, filter.getRegion())
                .stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public HolidayResponse getHolidayById(Long id) {
        HolidayMaster h = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found"));
        return map(h);
    }
    @Override
    public List<HolidayResponse> getYearlyCalendar(int year, String region) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        return repo.findByDateRangeAndRegion(start, end, region)
                .stream().map(this::map).collect(Collectors.toList());
    }

}
