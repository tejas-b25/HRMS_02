package com.example.User.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;   // instead of just employeeId

    @Column(length = 50, nullable = false)
    private String documentType;

    @Column(length = 255, nullable = false)
    private String fileName;

    @Column(length = 500, nullable = false)
    private String filePath;


    @Column(nullable = false)
    private String uploadedBy; // FK to Employee/HR/Admin

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime uploadedAt;


    @Column(nullable = false)
    private String status = "PENDING";

    @Lob
    private String remarks;


    @Column(nullable = false)
    private String storageType = "Local";

    @UpdateTimestamp
    private LocalDateTime verifiedAt;



    // Getters & Setters
}

