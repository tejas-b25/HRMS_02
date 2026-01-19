package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_360")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackThreeSixty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Column(nullable = false)
    private Long reviewerId;
    private String reviewerName;
    @Column(nullable = false)
    private Long revieweeId;
    private String revieweeName;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String comments;
    @Column(nullable = false)
    private Double rating;
    private LocalDateTime createdAt;
    private Boolean isDeleted = false;
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (isDeleted == null) isDeleted = false;
    }
}
