package com.example.User.util;

import com.example.User.entity.Employee;
import com.example.User.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificationUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Send Email
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
            System.out.println(" Email Sent → " + toEmail);
        } catch (Exception e) {
            System.out.println(" Email Sending Failed to: " + toEmail);
        }
    }

    // Push Notification
    public void sendPushNotification(Long employeeId, String message) {
        System.out.println(" Push → EmployeeId: " + employeeId + " | " + message);
    }

    // In-App Notification
    public void sendInAppNotification(Long employeeId, String message) {
        System.out.println(" In-App → EmployeeId: " + employeeId + " | " + message);
    }

    // Send To Multiple Employees
    public void sendNotificationToEmployees(Long[] employeeIds, String message,
                                            boolean email, boolean push, boolean inApp) {

        for (Long empId : employeeIds) {

            Optional<Employee> empOpt = employeeRepository.findById(empId);

            if (empOpt.isEmpty()) {
                System.out.println(" Employee not found: " + empId);
                continue;
            }

            Employee emp = empOpt.get();
            String targetEmail = emp.getEmail();  // actual email from DB

            if (email && targetEmail != null) {
                sendEmail(targetEmail, "New Announcement", message);
            }

            if (push) {
                sendPushNotification(empId, message);
            }

            if (inApp) {
                sendInAppNotification(empId, message);
            }
        }
    }
}
