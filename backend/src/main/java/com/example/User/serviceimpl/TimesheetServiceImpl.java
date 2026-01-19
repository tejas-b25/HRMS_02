package com.example.User.serviceimpl;

import com.example.User.dto.TimesheetRequestDTO;
import com.example.User.entity.Employee;
import com.example.User.entity.Timesheet;
import com.example.User.entity.TimesheetStatus;
import com.example.User.entity.User;
import com.example.User.exception.TimesheetException;
import com.example.User.repository.EmployeeRepository;
import com.example.User.repository.TimesheetRepository;
import com.example.User.repository.UserRepository;
import com.example.User.service.TimesheetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private EmployeeRepository employeeRepository;
    private final double maxDailyHours;

    private String mergeRemarks(String existing, String incoming) {
        if (existing == null || existing.isBlank()) return incoming;
        if (incoming == null || incoming.isBlank()) return existing;
        return existing + " | " + incoming;
    }

    public TimesheetServiceImpl(TimesheetRepository timesheetRepository,
                                @Value("${hrms.timesheet.maxDailyHours:24}") double maxDailyHours) {
        this.timesheetRepository = timesheetRepository;
        this.maxDailyHours = maxDailyHours;
    }


//
@Override
public Timesheet submitTimesheet(TimesheetRequestDTO dto) {

    //  Get the logged-in user
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();

    User loggedInUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new TimesheetException("Logged-in user not found"));

    String role = String.valueOf(loggedInUser.getRole());
    Long loggedInUserId = loggedInUser.getId();

    System.out.println("Logged-in user: " + username + " (Role: " + role + ", ID: " + loggedInUserId + ")");

    //  Fetch Employee record of logged-in user (if exists)
    Employee loggedInEmployee = employeeRepository.findByUser_Id(loggedInUserId)
            .orElse(null);

    // Validate access
    if (role.equalsIgnoreCase("EMPLOYEE")) {
        if (loggedInEmployee == null || !loggedInEmployee.getId().equals(dto.getEmployeeId())) {
            throw new TimesheetException("Access denied: Employees can only submit their own timesheet.");
        }
    } else if (role.equalsIgnoreCase("HR")) {
        // HR is allowed to submit for anyone — skip restriction
    } else {
        throw new TimesheetException("Access denied: Only Employees and HR can submit timesheets.");
    }

    //  Fetch Employee (target of the timesheet)
    Employee employee = employeeRepository.findById(dto.getEmployeeId())
            .orElseThrow(() -> new TimesheetException("Employee not found with ID: " + dto.getEmployeeId()));

    //  Business validations
    if (dto.getWorkDate().isAfter(LocalDate.now())) {
        throw new TimesheetException("workDate cannot be in the future.");
    }

    if (dto.getHoursWorked() > maxDailyHours) {
        throw new TimesheetException("hoursWorked exceeds configured daily limit of " + maxDailyHours);
    }

    // Prevent duplicate timesheet
    timesheetRepository.findByEmployee_IdAndProjectCodeAndTaskCodeAndWorkDate(
                    dto.getEmployeeId(), dto.getProjectCode(), dto.getTaskCode(), dto.getWorkDate())
            .ifPresent(t -> {
                throw new TimesheetException("Timesheet already exists for this employee on this date.");
            });

    //  Determine manager ID (if applicable)
    Long managerId = null;
    if (employee.getReportingManager() != null) {
        managerId = employee.getReportingManager().getId();
    }

    //  Build and save timesheet
    Timesheet t = Timesheet.builder()
            .employee(employee)
            .projectCode(dto.getProjectCode().trim())
            .taskCode(dto.getTaskCode().trim())
            .workDate(dto.getWorkDate())
            .hoursWorked(dto.getHoursWorked())
            .status(TimesheetStatus.SUBMITTED)
            .remarks(dto.getRemarks())
            .build();

    Timesheet saved = timesheetRepository.save(t);

    System.out.println("Timesheet submitted successfully by " + username);
    return saved;
}



    @Override
    public List<Timesheet> listTimesheetsForEmployee(Long employeeId) {
        return timesheetRepository.findByEmployeeIdOrderByWorkDateDesc(employeeId);
    }


    @Transactional
    @Override
    public Timesheet approveTimesheet(Long timesheetId, String remarks) {

        //  Fetch the timesheet
        Timesheet t = timesheetRepository.findById(timesheetId)
                .orElseThrow(() -> new TimesheetException("Timesheet not found: " + timesheetId));

        //  Check timesheet status
        if (t.getStatus() == TimesheetStatus.APPROVED) {
            throw new TimesheetException("Timesheet already approved.");
        }
        if (t.getStatus() == TimesheetStatus.REJECTED) {
            throw new TimesheetException("Cannot approve a rejected timesheet.");
        }

        //  Get logged-in user from SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     //   Long loggedInUserId = Long.parseLong(auth.getName()); // make sure your token stores User ID
        String username = auth.getName(); //
        User loggedInUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new TimesheetException("Logged-in user not found"));

        Long loggedInUserId = loggedInUser.getId();
        //  Fetch employee's reporting manager
        User reportingManager = t.getEmployee().getReportingManager();
        if (reportingManager == null || !reportingManager.getId().equals(loggedInUserId)) {
            throw new TimesheetException("Only the employee's reporting manager can approve this timesheet.");
        }

        //  Approve timesheet
        t.setStatus(TimesheetStatus.APPROVED);
        t.setApprovedBy(loggedInUserId);
        t.setRemarks(mergeRemarks(t.getRemarks(), remarks));
        t.setUpdatedAt(LocalDateTime.now());

        return timesheetRepository.save(t);
}


    @Transactional
    @Override
    public Timesheet rejectTimesheet(Long timesheetId, String remarks) {

        // Fetch the timesheet
        Timesheet t = timesheetRepository.findById(timesheetId)
                .orElseThrow(() -> new TimesheetException("Timesheet not found: " + timesheetId));

        // Check timesheet status
        if (t.getStatus() == TimesheetStatus.APPROVED) {
            throw new TimesheetException("Approved timesheet cannot rejected.");
        }


        //  Get logged-in user from SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //   Long loggedInUserId = Long.parseLong(auth.getName()); // make sure your token stores User ID
        String username = auth.getName(); // e.g., "superadmin"
        User loggedInUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new TimesheetException("Logged-in user not found"));

        Long loggedInUserId = loggedInUser.getId();
        //  Fetch employee's reporting manager
        User reportingManager = t.getEmployee().getReportingManager();
        if (reportingManager == null || !reportingManager.getId().equals(loggedInUserId)) {
            throw new TimesheetException("Only the employee's reporting manager can reject this timesheet.");
        }

        //  Approve timesheet
        t.setStatus(TimesheetStatus.REJECTED);
        t.setApprovedBy(loggedInUserId);
        t.setRemarks(mergeRemarks(t.getRemarks(), remarks));
        t.setUpdatedAt(LocalDateTime.now());

        return timesheetRepository.save(t);
    }



    @Override
    public List<Timesheet> listPendingForManager(Long managerId) {
        //  Get the logged-in user from Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // e.g. "dhiraj", "admin", etc.
        User loggedInUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new TimesheetException("Logged-in user not found"));

        Long loggedInUserId = loggedInUser.getId();
        String role = String.valueOf(loggedInUser.getRole());

        System.out.println("Logged in user: " + username + " (Role: " + role + ", ID: " + loggedInUserId + ")");

        // If HR or Admin → can see ALL pending timesheets
        if (role.equalsIgnoreCase("HR") || role.equalsIgnoreCase("ADMIN")) {
            List<Timesheet> allPending = timesheetRepository.findByStatus(TimesheetStatus.SUBMITTED);
            System.out.println("HR/Admin access: Returning all pending timesheets = " + allPending.size());
            return allPending;
        }

        // If Manager → can see only their team’s pending timesheets
        if (role.equalsIgnoreCase("MANAGER")) {
            // Fetch the Employee record of the logged-in manager
            Employee managerEmployee = employeeRepository.findById(loggedInUserId)
                    .orElseThrow(() -> new TimesheetException("Employee record not found for logged-in user"));

            // Validate: The manager can only view their own team
            if (!managerEmployee.getId().equals(managerId)) {
                throw new TimesheetException("Access denied: You can only view your own team’s timesheets.");
            }

            // Fetch all team members under this manager
            List<Long> teamMemberIds = employeeRepository.findByReportingManager_Id(managerId)
                    .stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList());

            System.out.println("Manager ID: " + managerId);
            System.out.println("Team member IDs: " + teamMemberIds);

            // Fetch all SUBMITTED timesheets for those employees
            List<Timesheet> pending = timesheetRepository.findByStatusAndEmployee_IdIn(
                    TimesheetStatus.SUBMITTED, teamMemberIds);

            pending.forEach(ts -> System.out.println("Timesheet ID: " + ts.getTimesheetId() +
                    ", Employee ID: " + ts.getEmployee().getId() +
                    ", Name: " + ts.getEmployee().getName() +
                    ", Status: " + ts.getStatus()));

            return pending;
        }

        //  Other roles (employees, etc.) not allowed
        throw new TimesheetException("Access denied: Only HR, Admin, or Managers can view pending timesheets.");
    }




}
