package com.example.User.service;

public interface PasswordService {
    public void generateOtp(String email);
    public void resetPassword(String email, String otp, String newPassword);
}
