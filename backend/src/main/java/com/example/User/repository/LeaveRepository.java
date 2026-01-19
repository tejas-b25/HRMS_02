package com.example.User.repository;

import com.example.User.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployeeId(Long employeeId);

    @Query("select l from Leave l where l.employeeId = :employeeId and (l.status = 'PENDING' or l.status = 'APPROVED') and ( (l.startDate <= :endDate and l.endDate >= :startDate) )")
    List<Leave> findOverlappingLeaves(@Param("employeeId") Long employeeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    List<Leave> findByStartDateBetween(LocalDate fromDate, LocalDate toDate);
}
