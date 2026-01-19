package com.example.User.service;

import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {
    String exportEmployeeMasterReport(String exportType) throws IOException;
    String exportAttendanceReport(String exportType, LocalDate fromDate, LocalDate toDate) ;
    String exportComplianceSummary(String exportType, LocalDate fromDate, LocalDate toDate);
    String exportLeaveReport(String exportType, LocalDate fromDate, LocalDate toDate);

}