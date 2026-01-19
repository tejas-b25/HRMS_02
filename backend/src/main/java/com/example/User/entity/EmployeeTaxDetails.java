package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_tax_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeTaxDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id", updatable = false, nullable = false)
    private Long taxId;

    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn (name = "employee_id")
    private Long employeeId; // FK → employee_master

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_config_id")
    private TaxConfiguration taxConfiguration;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_regime", nullable = false)
    private TaxRegime taxRegime;

    @Column(name = "declaration_text", columnDefinition = "TEXT")
    private String declarationText;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public enum TaxRegime {
        OLD, NEW
    }
}
