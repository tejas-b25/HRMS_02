package com.example.User.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Data
public class AnnouncementRequestDTO {

    private String title;
    private String description;
    private String category;
    private String priority;
    private String targetDepartment;
    private String targetLocation;
    private String targetRoles;

    private LocalDateTime publishDate;
    private LocalDateTime expireDate;

    private MultipartFile attachment;

    private boolean email;
    private boolean push;
    private boolean inApp;

    private Long[] employeeIds;


}