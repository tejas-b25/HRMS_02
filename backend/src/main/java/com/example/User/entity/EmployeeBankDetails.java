package com.example.User.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity

@Table(name = "employee_bank_details",
        uniqueConstraints = @UniqueConstraint(columnNames = "employee_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeBankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_bank_details_id")
    private Long employeeBankDetailsId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId", nullable = false)
    @JsonIgnoreProperties({"documents", "attendances"})
    private Employee employee;


    @NotBlank(message = "Bank name is required")
    @Size(max = 50)
    @Column(name = "bank_name")
    private String bankName;

    @NotBlank(message = "Account holder name is required")
    @Size(max = 30)
    @Column(name = "account_holder_name")
    private String accountHolderName;

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[0-9]{10,30}$", message = "Invalid account number")
    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @NotBlank(message = "Account type is required")
    @Size(max = 30)
    @Column(name = "account_type")
    private String accountType;

    @NotBlank(message = "IFSC code is required")
    @Pattern(regexp = "^[A-Za-z]{4}0[A-Za-z0-9]{6}$", message = "Invalid IFSC format")
    @Size(max = 20)
    @Column(name = "ifsc")
    private String ifsc;

    @NotBlank(message = "Branch name is required")
    @Size(max = 30)
    @Column(name = "branch")
    private String branch;

    @NotBlank(message = "UAN number is required")
    @Pattern(regexp = "^[0-9]{12}$", message = "UAN must be 12 digits")
    @Column(name = "uan_number")
    private String uanNumber;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active")
    private boolean active = true;
}
