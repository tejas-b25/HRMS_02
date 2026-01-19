package com.example.User.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String category;
    private String priority;
    private String attachmentUrl;

    private String targetDepartment;
    private String targetLocation;
    private String targetRoles;

    private LocalDateTime publishDate;
    private LocalDateTime expireDate;

    // ---------------- Added by Harshada ----------------
    private Long createdById;
    private String createdByName;
    private String createdByRole;
    // ---------------------------------------------------

    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "ADMIN";
        this.isActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "ADMIN";
    }
}