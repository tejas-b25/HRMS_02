package com.example.User.serviceimpl;

import com.example.User.dto.AttendanceReportRow;
import com.example.User.dto.ComplianceSummaryDTO;
import com.example.User.dto.LeaveReportRow;
import com.example.User.entity.*;
import com.example.User.repository.*;
import com.example.User.service.ReportService;
import com.example.User.util.ReportExportUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final ReportExportUtil reportExportUtil;
    private final EmployeeComplianceRepository reportRepository;
    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository;
    @Value("${report.export.path}")
    private String exportPath;
    @Override
    public String exportEmployeeMasterReport(String exportType) {
        List<Employee> employees = employeeRepository.findAll();

        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("employeeId", "Employee ID");
        columns.put("firstName", "First Name");
        columns.put("lastName", "Last Name");
        columns.put("email", "Email");
        columns.put("department", "Departement");
        columns.put("status", "Status");
        columns.put("createdAt", "Created Date");
        columns.put("updatedAt", "Updation Date");

        File directory = new File(exportPath);
        if (!directory.exists()) directory.mkdirs();

        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileName = "Employee_Master_Report_" + timestamp + "." + exportType;
        String filePath = exportPath + File.separator + fileName;
        try {
            return reportExportUtil.export(employees, columns, "EmployeeMaster_Report", exportType, exportPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String exportAttendanceReport(String exportType, LocalDate fromDate, LocalDate toDate) {
        List<Attendance> attendanceList =
                attendanceRepository.findByAttendanceDateBetween(fromDate, toDate);

        List<AttendanceReportRow> rows = attendanceList.stream().map(a ->
                AttendanceReportRow.builder()
                        .attendanceId(a.getAttendanceId())
                        .employeeId(a.getEmployee().getEmployeeId())
                        .employeeName(a.getEmployee().getFirstName() + " " +
                                (a.getEmployee().getLastName() != null ? a.getEmployee().getLastName() : ""))
                        .attendanceDate(a.getAttendanceDate())
                        .clockInTime(a.getClockInTime())
                        .clockOutTime(a.getClockOutTime())
                        .status(a.getStatus().name())
                        .deviceType(a.getDeviceType().name())
                        .geoLocation(a.getGeoLocation())
                        .workingHours(calculateWorkingHours(a.getClockInTime(), a.getClockOutTime())) // 👈 Add working hours
                        .createdAt(a.getCreatedAt())
                        .updatedAt(a.getUpdatedAt())
                        .build()
        ).toList();
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("attendanceId", "AttendanceId");
        columns.put("employeeId", "EmployeeId");
        columns.put("employeeName", "Employee Name");
        columns.put("attendanceDate", "Date");
        columns.put("clockInTime", "Check-In");
        columns.put("clockOutTime", "Check-Out");
        columns.put("workingHours", "Working Hours");
        columns.put("status", "Status");
        columns.put("deviceType", "Device");
        columns.put("geoLocation", "Geo Location");
        if (exportType.equalsIgnoreCase("excel") || exportType.equalsIgnoreCase("csv")) {
            columns.put("createdAt", "Created At");
            columns.put("updatedAt", "Updated At");
        }
        // columns.put("createdAt", "Created At");
        // columns.put("updatedAt", "Updated At");
        try {
            return reportExportUtil.export(
                    rows,
                    columns,
                    "Attendance_Report",
                    exportType,
                    exportPath
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to export attendance report: " + e.getMessage());
        }
    }

    @Override
    public String exportComplianceSummary(String exportType, LocalDate fromDate, LocalDate toDate) {
        List<ComplianceSummaryDTO> reportData = getComplianceSummary(fromDate, toDate);
        if (reportData.isEmpty()) {
            throw new RuntimeException("No compliance records found in given date range.");
        }
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("employeeId", "Employee ID");
        columns.put("complianceName", "Compliance Name");
        columns.put("complianceType", "Compliance Type");
        columns.put("status", "Status");
        columns.put("submittedDate", "Submitted Date");
        columns.put("dueDate", "Due Date");
        columns.put("verifiedBy", "Verified By");
        columns.put("remarks", "Remarks");
        columns.put("updatedAt", "Last Updated");
        String fileName = "Compliance_Summary_" + System.currentTimeMillis() +
                (exportType.equalsIgnoreCase("excel") ? ".xlsx" : ".csv");
        String fullPath = exportPath + "/" + fileName;
        try {
            return reportExportUtil.export(reportData, columns, "Compliance Summary Report", exportType, exportPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ComplianceSummaryDTO> getComplianceSummary(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime start = fromDate.atStartOfDay();
        LocalDateTime end = toDate.atTime(23, 59, 59);
        List<Object[]> resultList = reportRepository.getComplianceSummaryData(start, end);
        if (resultList.isEmpty()) {
            throw new RuntimeException("No compliance records found in given date range.");
        }
        return resultList.stream()
                .map(row -> new ComplianceSummaryDTO(
                        (Long) row[0],
                        (String) row[1],
                        (ComplianceMaster.ComplianceType) row[2],
                        (EmployeeComplianceMapping.Status) row[3],
                        (LocalDate) row[4],
                        (LocalDate) row[5],
                        (String) row[6],
                        (String) row[7],
                        (LocalDateTime) row[8]
                )).toList();
    }
    @Override
    public String exportLeaveReport(String exportType, LocalDate fromDate, LocalDate toDate) {
        List<Leave> leaveList = leaveRepository.findByStartDateBetween(fromDate, toDate);
        if (leaveList.isEmpty()) {
            throw new RuntimeException("No leave records found in given date range.");
        }
        List<LeaveReportRow> rows = leaveList.stream().map(l -> {
            // Use array wrapper to allow mutation inside lambda
            final String[] approverName = {""};
            final String[] approverRole = {""};
            if (l.getApproverId() != null) {
                userRepository.findById(l.getApproverId()).ifPresent(user -> {
                    approverName[0] = user.getFirstName() +
                            (user.getLastName() != null ? " " + user.getLastName() : "");
                    approverRole[0] = user.getRole().name();
                });
            }
            Employee emp = employeeRepository.findById(l.getEmployeeId()).orElse(null);
            String employeeName = emp != null
                    ? emp.getFirstName() + (emp.getLastName() != null ? " " + emp.getLastName() : "")
                    : "N/A";
            return new LeaveReportRow(
                    l.getLeaveId(),
                    l.getEmployeeId(),
                    employeeName,
                    l.getLeaveType().getName(),
                    l.getStartDate(),
                    l.getEndDate(),
                    l.getTotalDays(),
                    l.getReason(),
                    l.getStatus().name(),
                    l.getCreatedAt(),
                    l.getUpdatedAt(),
                    approverName[0],
                    approverRole[0]
            );
        }).toList();
        // Column mapping
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("leaveId", "Leave ID");
        columns.put("employeeId", "Employee ID");
        columns.put("leaveType", "Leave Type");
        columns.put("startDate", "Start Date");
        columns.put("endDate", "End Date");
        columns.put("totalDays", "Total Days");
        columns.put("status", "Status");
        columns.put("approverName", "Approver Name");
        if (exportType.equalsIgnoreCase("excel") || exportType.equalsIgnoreCase("csv")) {
            columns.put("approverRole", "Approver Role");
        }
        columns.put("appliedDate", "Applied Date");
        columns.put("approvedDate", "Approved Date");
        try {
            return reportExportUtil.export(
                    rows,
                    columns,
                    "Leave_Report",
                    exportType,
                    exportPath
            );
        } catch (Exception e) {
            throw new RuntimeException("Error creating leave report: " + e.getMessage());
        }
    }
    private String calculateWorkingHours(LocalDateTime in, LocalDateTime out) {
        if (in == null || out == null) return "00:00:00";
        Duration duration = Duration.between(in, out);
        long hours, minutes, seconds;

        try {
            hours = duration.toHours();
            minutes = duration.toMinutesPart();
            seconds = duration.toSecondsPart();
        } catch (NoSuchMethodError e) {
            hours = duration.toHours();
            minutes = (duration.toMinutes() % 60);
            seconds = (duration.getSeconds() % 60);
        }
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
