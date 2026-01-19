package com.example.User.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnnouncementResponseDTO {
    private Long announcementId;
    private String title;
    private String description;
    private String category;
    private String priority;
    private String attachmentUrl;
    private String targetDepartment;
    private String targetLocation;
    private String targetRoles;
    private LocalDateTime publishDate;
    private LocalDateTime expireDate;
    private String createdBy;
    private String createdByRole;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}