package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @Column(nullable = false)
    private Long complianceId;

    @Column(nullable = false)
    private Long complianceMappingId;

    private String fileName;
    private String fileType;
    private String filePath;
    private Long fileSizeBytes;
    private String uploadedBy;
    private LocalDateTime uploadedAt;
    private LocalDate expiryDate;
    private Boolean isVerified;
    private String verifiedBy;
    private LocalDateTime verifiedAt;
    //private Long employeeId;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private DocumentStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    @Column(name = "employee_id")
    private Long employeeId;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        uploadedAt = LocalDateTime.now();
        status = DocumentStatus.PendingVerification;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum DocumentStatus {
        Active, Expired, Removed, PendingVerification
    }
}
