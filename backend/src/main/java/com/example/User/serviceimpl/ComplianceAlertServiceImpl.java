package com.example.User.serviceimpl;

import com.example.User.dto.ComplianceAlertRequestDto;
import com.example.User.entity.AlertStatus;
import com.example.User.entity.ComplianceAlert;
import com.example.User.repository.ComplianceAlertRepository;
import com.example.User.service.ComplianceAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplianceAlertServiceImpl implements ComplianceAlertService {
    @Autowired
    private ComplianceAlertRepository alertRepository;

    @Override
    public ComplianceAlert createAlert(ComplianceAlertRequestDto request) {
        ComplianceAlert alert = ComplianceAlert.builder()
                .complianceId(request.getComplianceId())
                .complianceMappingId(request.getComplianceMappingId())
                .alertType(request.getAlertType())
                .alertDate(request.getAlertDate())
                .triggerDateTime(request.getTriggerDateTime())
                .channel(request.getChannel())
                .recipients(request.getRecipients())
                .messageTemplateId(request.getMessageTemplateId())
                .messagePayload(request.getMessagePayload())
                .status(AlertStatus.PENDING)
                .retryCount(0)
                .sentAt(LocalDateTime.now())
                .lastRetryAt(LocalDateTime.now())
                .createdBy(request.getCreatedBy())
                .remarks(request.getRemarks())
                .build();

        return alertRepository.save(alert);
    }

    @Override
    public ComplianceAlert getAlertById(Long alertId) {
        return alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found with ID: " + alertId));
    }

    @Override
    public List<ComplianceAlert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @Override
    public List<ComplianceAlert> getUpcomingAlerts(int days) {
        LocalDate today = LocalDate.now();
        LocalDate upcoming = today.plusDays(days);
        return alertRepository.findByAlertDateBetween(today, upcoming);
    }

    @Override
    public List<ComplianceAlert> getAlertHistory(String status, String channel, LocalDate fromDate, LocalDate toDate) {
        return alertRepository.findAlertHistory(status, channel, fromDate, toDate);
    }

    @Override
    public boolean deleteAlert(Long alertId) {
        Optional<ComplianceAlert> alertOptional = alertRepository.findById(alertId);

        if (alertOptional.isPresent()) {
            ComplianceAlert alert = alertOptional.get();


            alert.setStatus(AlertStatus.CANCELLED);
            alertRepository.save(alert);


            return true;
        }
        return false;
    }

    @Override
    public ComplianceAlert updateAlert(Long alertId, ComplianceAlertRequestDto request) {
        ComplianceAlert existingAlert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found with ID: " + alertId));


        if (request.getAlertType() != null) {
            existingAlert.setAlertType(request.getAlertType());
        }

        if (request.getAlertDate() != null) {
            existingAlert.setAlertDate(request.getAlertDate());
        }

        if (request.getTriggerDateTime() != null) {
            existingAlert.setTriggerDateTime(request.getTriggerDateTime());
        }

        if (request.getChannel() != null) {
            existingAlert.setChannel(request.getChannel());
        }

        if (request.getRecipients() != null && !request.getRecipients().isEmpty()) {
            existingAlert.setRecipients(request.getRecipients());
        }

        if (request.getMessageTemplateId() != null) {
            existingAlert.setMessageTemplateId(request.getMessageTemplateId());
        }

        if (request.getMessagePayload() != null) {
            existingAlert.setMessagePayload(request.getMessagePayload());
        }

        if (request.getStatus() != null) {
            existingAlert.setStatus(request.getStatus());
        }

        if (request.getRemarks() != null) {
            existingAlert.setRemarks(request.getRemarks());
        }
        if (request.getSentAt() != null) {
            existingAlert.setSentAt(request.getSentAt());
        }
        if (request.getLastRetryAt() != null) {
            existingAlert.setLastRetryAt(request.getLastRetryAt());
        }


        if (request.getUpdatedBy() != null) {
            existingAlert.setUpdatedBy(request.getUpdatedBy());
        } else {
            existingAlert.setUpdatedBy("system");
        }

        return alertRepository.save(existingAlert);
    }


}
