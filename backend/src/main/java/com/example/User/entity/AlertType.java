package com.example.User.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AlertType {

    REMINDER,
    EXPIRY,
    RENEWAL,
    OVERDUE,
    CUSTOM;

    // ✅ ADD THIS METHOD
    @JsonCreator
    public static AlertType from(String value) {
        return AlertType.valueOf(value.toUpperCase());
    }
}
