package com.example.User.util;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    // IFSC: 11 characters, alphanumeric, 4 letters + 0 + 6 digits
    public void validateIFSC(String ifsc) {
        if (ifsc == null || !ifsc.matches("^[A-Z]{4}0[A-Z0-9]{6}$")) {
            throw new RuntimeException("Invalid IFSC Code Format");
        }
    }

    // Account Number: numeric 10-30
    public void validateAccountNumber(String accountNumber) {
        if (accountNumber == null || !accountNumber.matches("^[0-9]{10,30}$")) {
            throw new RuntimeException("Invalid Account Number");
        }
    }

    // UAN: 12 digit number
    public void validateUAN(String uan) {
        if (uan == null || !uan.matches("^[0-9]{12}$")) {
            throw new RuntimeException("Invalid UAN Number (must be 12 digits)");
        }
    }
}

