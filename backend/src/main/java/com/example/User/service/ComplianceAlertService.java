package com.example.User.service;

import com.example.User.dto.ComplianceAlertRequestDto;
import com.example.User.entity.ComplianceAlert;

import java.time.LocalDate;
import java.util.List;

public interface ComplianceAlertService {
    ComplianceAlert createAlert(ComplianceAlertRequestDto request);

    ComplianceAlert getAlertById(Long alertId);

    List<ComplianceAlert> getAllAlerts();

    List<ComplianceAlert> getUpcomingAlerts(int days);

    List<ComplianceAlert> getAlertHistory(String status, String channel, LocalDate fromDate, LocalDate toDate);

    boolean deleteAlert(Long alertId);

    ComplianceAlert updateAlert(Long alertId, ComplianceAlertRequestDto request);
}
