package com.example.User.util;

import com.example.User.entity.Announcement;
import com.example.User.entity.Employee;
import com.example.User.repository.AnnouncementRepository;
import com.example.User.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private NotificationUtil notificationUtil;

    @Scheduled(fixedRate = 300000)   // Runs every 5 minutes
    public void expireAndNotify() {

        List<Announcement> activeAnnouncements = announcementRepository.findByIsActiveTrue();

        for (Announcement a : activeAnnouncements) {

            // 1️ Expire announcement automatically
            if (a.getExpireDate() != null && a.getExpireDate().isBefore(LocalDateTime.now())) {
                a.setIsActive(false);
                announcementRepository.save(a);
                System.out.println("Announcement expired: " + a.getTitle());
            }

            // 2️ Publish when publish time arrives
            if (a.getPublishDate() != null &&
                    a.getPublishDate().isBefore(LocalDateTime.now()) &&
                    !Boolean.TRUE.equals(a.getIsActive())) {

                Long[] targetEmployees = findAllEmployees();

                notificationUtil.sendNotificationToEmployees(
                        targetEmployees,
                        "New Announcement: " + a.getTitle(),
                        true,
                        true,
                        true
                );

                a.setIsActive(true);
                announcementRepository.save(a);
            }
        }
    }

    //  Get ALL employee IDs
    private Long[] findAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(Employee::getEmployeeId)
                .toArray(Long[]::new);
    }
}
