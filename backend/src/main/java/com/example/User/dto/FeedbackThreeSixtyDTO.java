package com.example.User.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackThreeSixtyDTO {
    private Long feedbackId;
    @NotNull(message = "Reviewer (User ID) is required")
    private Long reviewerId;
    private String reviewerName;
    @NotNull(message = "Reviewee (Employee ID) is required")
    private Long revieweeId;
    private String revieweeName;
    @NotBlank(message = "Comments are required")
    private String comments;
    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5.0")
    private Double rating;
    private LocalDateTime createdAt;
    private Boolean isDeleted = false;
}
