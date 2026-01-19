package com.example.User.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceDocumentResponseDto {
    private Long documentId;
    private Long complianceId;
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
    private String status;
    private Long employeeId;
}
