package com.example.User.repository;

import com.example.User.entity.ShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long> {
    @Query("SELECT sa FROM ShiftAssignment sa WHERE sa.employeeId = :empId AND sa.isActive = true " +
            "AND ( (sa.effectiveFromDate <= :toDate) AND (sa.effectiveToDate >= :fromDate) )")
    List<ShiftAssignment> findOverlappingAssignments(@Param("empId") Long employeeId,
                                                     @Param("fromDate") LocalDate fromDate,
                                                     @Param("toDate") LocalDate toDate);

    Optional<ShiftAssignment> findTopByEmployeeIdAndIsActiveTrueOrderByEffectiveFromDateDesc(Long employeeId);


}
