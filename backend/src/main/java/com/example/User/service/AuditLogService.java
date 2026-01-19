package com.example.User.service;

public interface AuditLogService {

    public void saveAudit(String performedBy, String action, String details);


    void logEvent(String username, String login);
}