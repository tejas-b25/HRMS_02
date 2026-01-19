package com.example.User.dto;

import com.example.User.entity.AlertStatus;
import com.example.User.entity.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.nio.channels.Channel;
import com.example.User.entity.Channel;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceAlertRequestDto {
    private Long complianceId;
    private Long complianceMappingId;
    private AlertType alertType;
    private LocalDate alertDate;
    private LocalDateTime triggerDateTime;
    private Channel channel;
    private String recipients;
    private String messageTemplateId;
    private String messagePayload;
    private String createdBy;
    private String remarks;
    private AlertStatus status;
    private String updatedBy;
    private LocalDateTime sentAt;
    private LocalDateTime lastRetryAt;

}
