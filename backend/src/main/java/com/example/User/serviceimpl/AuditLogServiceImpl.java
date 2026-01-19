package com.example.User.serviceimpl;

import com.example.User.entity.AuditLog;
import com.example.User.repository.AuditLogRepository;
import com.example.User.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private final AuditLogRepository auditLogRepository;

    public void logEvent(String username, String action) {
        if(username == null || username.isBlank()) {
            username = "SYSTEM";  // default if blank
        }
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setPerformedBy(username);
        log.setDetails(action + " performed by " + username);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);

    }

    @Override
    public void saveAudit(String performedBy, String action, String details) {
        AuditLog log = new AuditLog();
        log.setPerformedBy(performedBy);
        log.setAction(action);
        log.setDetails(details);
        log.setCreatedAt(LocalDateTime.now());
        auditLogRepository.save(log);
    }



}
