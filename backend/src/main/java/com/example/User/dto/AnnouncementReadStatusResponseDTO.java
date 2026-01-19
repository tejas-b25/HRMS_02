package com.example.User.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnnouncementReadStatusResponseDTO {
    private Long readId;
    private Long announcementId;
    private Long userId;
    private Boolean acknowledged;
    private LocalDateTime readAt;
    private LocalDateTime acknowledgedAt;
}