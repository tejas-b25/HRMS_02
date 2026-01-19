package com.example.User.repository;

import com.example.User.entity.ComplianceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ComplianceAlertRepository extends
        JpaRepository<ComplianceAlert, Long> {
    List<ComplianceAlert> findByAlertDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT a FROM ComplianceAlert a " +
            "WHERE (:status IS NULL OR a.status = :status) " +
            "AND (:channel IS NULL OR a.channel = :channel) " +
            "AND (:fromDate IS NULL OR a.alertDate >= :fromDate) " +
            "AND (:toDate IS NULL OR a.alertDate <= :toDate) " +
            "ORDER BY a.alertDate DESC")
    List<ComplianceAlert> findAlertHistory(@Param("status") String status,
                                           @Param("channel") String channel,
                                           @Param("fromDate") LocalDate fromDate,
                                           @Param("toDate") LocalDate toDate);

}
