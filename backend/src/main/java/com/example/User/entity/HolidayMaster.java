package com.example.User.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "holiday_master",
        uniqueConstraints = {
                @UniqueConstraint(name = "India_holiday_date_region", columnNames = {"holiday_date", "region"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayMaster {

    @Id
    @GeneratedValue
    @Column(name = "holiday_id", updatable = false, nullable = false)
    private Long holidayId;

    @Column(name = "holiday_name", nullable = false, length = 100)
    private String holidayName;

    @Column(name = "holiday_date", nullable = false)
    private LocalDate holidayDate;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "is_optional", nullable = false)
    private boolean isOptional = false;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "holiday_type", nullable = false, length = 20)
//    private HolidayType holidayType;

    @Column(name = "description", length = 255)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;
    @Enumerated(EnumType.STRING)
    private HolidayType holidayType;
}
