package com.example.User.util;

public class MaskUtil {

    public static String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "XXXX";
        }
        int visibleDigits = 4;
        int maskLength = accountNumber.length() - visibleDigits;

        return "X".repeat(maskLength) + accountNumber.substring(maskLength);
    }
}

