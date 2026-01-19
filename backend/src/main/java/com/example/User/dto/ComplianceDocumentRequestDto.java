package com.example.User.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceDocumentRequestDto {
    private Long complianceId;
    private Long complianceMappingId;
    private MultipartFile file;
    private LocalDate expiryDate;
    private String uploadedBy;
    private Long employeeId;

}
