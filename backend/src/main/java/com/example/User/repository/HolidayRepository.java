package com.example.User.repository;

import com.example.User.entity.HolidayMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<HolidayMaster, Long>{
    @Query("""
        select case when count(h) > 0 then true else false end
        from HolidayMaster h
        where h.holidayDate = :date
          and ( (:region is null and h.region is null) or (:region is not null and h.region = :region) )
          and (:excludingId is null or h.holidayId <> :excludingId)
        """)
    boolean existsByHolidayDateAndRegionAndIdNot(
            @Param("date") LocalDate date,
            @Param("region") String region,
            @Param("excludingId") Long excludingId
    );

    @Query("""
        select h from HolidayMaster h
        where (:start is null or h.holidayDate >= :start)
          and (:end is null or h.holidayDate <= :end)
          and (:region is null or h.region = :region)
        order by h.holidayDate asc
        """)
    List<HolidayMaster> findByDateRangeAndRegion(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("region") String region
    );
}
