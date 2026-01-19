package com.example.User.service;

import com.example.User.dto.TimesheetRequestDTO;
import com.example.User.entity.Employee;
import com.example.User.entity.Timesheet;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TimesheetService {
    Timesheet submitTimesheet(TimesheetRequestDTO dto);

    //Timesheet submitTimesheet(TimesheetRequestDTO dto, Employee employee);

    List<Timesheet> listTimesheetsForEmployee(Long employeeId);

   // Timesheet approveTimesheet(Long timesheetId, Long approvedBy, String remarks);

    @Transactional
    Timesheet approveTimesheet(Long timesheetId, String remarks);

   // @Transactional
  //  Timesheet rejectTimesheet(Long timesheetId, Long approvedBy, String remarks);

    @Transactional
    Timesheet rejectTimesheet(Long timesheetId, String remarks);

  //  List<Timesheet> listPendingForManager(Long managerId);

    //
   // List<Timesheet> listPendingForManager(Long managerId, Long loggedInUserId);

    //
    List<Timesheet> listPendingForManager(Long managerId);

    //  TimesheetRequestDTO submitTimesheet(TimesheetRequestDTO dto);
}
