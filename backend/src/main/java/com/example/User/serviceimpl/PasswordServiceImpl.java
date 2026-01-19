package com.example.User.serviceimpl;

import com.example.User.entity.PasswordResetToken;
import com.example.User.entity.User;
import com.example.User.repository.PasswordResetTokenRepository;
import com.example.User.repository.UserRepository;
import com.example.User.service.EmailService;
import com.example.User.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void generateOtp(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        PasswordResetToken token = new PasswordResetToken();
        token.setEmail(email);
        token.setOtp(otp);
        token.setExpiryTime(expiry);

        tokenRepository.save(token);

        emailService.sendEmail(email, "Your OTP Code", "Your OTP is: " + otp);
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        PasswordResetToken token = tokenRepository.findByEmailAndOtp(email, otp)
                .orElseThrow(() -> new RuntimeException("Invalid OTP"));

        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPassword(hashedPassword);

        userRepository.save(user);

        tokenRepository.delete(token); // cleanup used OTP
    }
}

