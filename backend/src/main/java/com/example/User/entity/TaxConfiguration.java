package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tax_configuration",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"regime_type", "effective_from"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_config_id", updatable = false, nullable = false)
    private Long taxConfigId;

    @Enumerated(EnumType.STRING)
    @Column(name = "regime_type", nullable = false)
    private RegimeType regimeType;

    @Column(name = "rebate_limit", nullable = false, precision = 12, scale = 2)
    private BigDecimal rebateLimit;

    @Column(name = "exemption_rules", columnDefinition = "TEXT")
    private String exemptionRules; // JSON/Text for slab rules

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public enum RegimeType {
        OLD, NEW
    }
}