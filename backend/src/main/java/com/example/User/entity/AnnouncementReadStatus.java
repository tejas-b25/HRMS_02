package com.example.User.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "AnnouncementReadStatus")
public class AnnouncementReadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcementId", nullable = false)
    private Announcement announcement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private LocalDateTime readAt;
    private Boolean acknowledged;
    private LocalDateTime acknowledgedAt;
}