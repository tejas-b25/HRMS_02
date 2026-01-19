package com.example.User.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;
    private String gender;

    @Column(unique = true, nullable = false)
    private String contactNumber;

    @Column(unique = true, nullable = false)
    private String email;

    private String address;
    private String bloodGroup;

    private LocalDate dateOfJoining;

    private String designation;
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    private String status = "ACTIVE";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // ---- Reporting Manager ----
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporting_manager_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"employee", "employees"})
    private User reportingManager;

    // ---- HRBP ----
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hrbp_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"employee", "employees"})
    private User hrbp;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public String getName() {
        return firstName + (lastName != null ? " " + lastName : "");
    }

    // ---- Documents ----
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("employee")
    private List<Document> documents = new ArrayList<>();

    // ---- User ----
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties("employee")
    private User user;

    // ---- Attendances ----
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("employee")
    private List<Attendance> attendances = new ArrayList<>();

    public Long getId() {
        return employeeId;
    }
}
