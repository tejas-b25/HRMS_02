package com.example.User.controller;

import com.example.User.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class PasswordController {
    @Autowired
    private PasswordService passwordService;  // using interface

    @PostMapping("/requestOtp")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        passwordService.generateOtp(request.get("email"));
        return ResponseEntity.ok("OTP sent to registered email");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        passwordService.resetPassword(
                request.get("email"),
                request.get("otp"),
                request.get("newPassword")
        );
        return ResponseEntity.ok("Password reset successful");
    }
}
