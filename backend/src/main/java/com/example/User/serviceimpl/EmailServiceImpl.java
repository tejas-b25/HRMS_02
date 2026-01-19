package com.example.User.serviceimpl;

import com.example.User.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public  void sendEmail(String to, String subject, String body) {
        // Implement JavaMailSender logic here
        System.out.println("Sending email to " + to + " : " + body);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("your_email@gmail.com"); // must match spring.mail.username

        mailSender.send(message);
        System.out.println("Email sent successfully to " + to);
    }
}