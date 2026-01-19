package com.example.User.exception;

public class LeaveTypeNotFoundException extends RuntimeException {
    public LeaveTypeNotFoundException(String message) {
        super(message);
    }
}

