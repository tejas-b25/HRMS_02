package com.example.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "compliance_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    private Long complianceId;
    private Long complianceMappingId;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    private LocalDate alertDate;
    private LocalDateTime triggerDateTime;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    @Column(length = 1024)
    private String recipients;

    private String messageTemplateId;

    @Column(columnDefinition = "TEXT")
    private String messagePayload;

    @Enumerated(EnumType.STRING)
    private AlertStatus status;

    private LocalDateTime sentAt;
    private Integer retryCount;
    private LocalDateTime lastRetryAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private String createdBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String remarks;
    private String updatedBy;


}
