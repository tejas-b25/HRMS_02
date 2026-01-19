package com.example.User.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BenefitProviderDTO {
    private Long providerId;
    private String providerName;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String policyNumber;
    private LocalDate renewalDate;
    private String address;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

