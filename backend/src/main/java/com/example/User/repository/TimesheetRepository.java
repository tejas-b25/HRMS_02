package com.example.User.repository;

import com.example.User.entity.Timesheet;
import com.example.User.entity.TimesheetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    Optional<Timesheet> findByEmployee_IdAndProjectCodeAndTaskCodeAndWorkDate(
            Long employeeId, String projectCode, String taskCode, LocalDate workDate);

    List<Timesheet> findByEmployeeIdOrderByWorkDateDesc(Long employeeId);

    List<Timesheet> findByStatusAndEmployee_IdIn(TimesheetStatus timesheetStatus, List<Long> teamMemberIds);

    List<Timesheet> findByStatus(TimesheetStatus timesheetStatus);
}
