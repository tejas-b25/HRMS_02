package com.example.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_master",
        uniqueConstraints = @UniqueConstraint(columnNames = {"complianceName", "complianceType"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceMaster {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid2")
    private Long complianceId;

    private String complianceName;

    @Enumerated(EnumType.STRING)
    private ComplianceType complianceType;

    private String description;
    private Boolean isActive = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum ComplianceType {
        PF, ESI, TDS, PT, Gratuity, Other
    }
}
