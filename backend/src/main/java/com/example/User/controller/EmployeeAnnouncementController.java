package com.example.User.controller;
import com.example.User.dto.AnnouncementResponseDTO;
import com.example.User.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/announcements/employee")
@CrossOrigin("*")
public class EmployeeAnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<AnnouncementResponseDTO>> getActiveAnnouncements() {
        return ResponseEntity.ok(announcementService.getActiveAnnouncements());
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(
            @PathVariable Long id,
            Authentication authentication) {

        announcementService.markAsRead(id, authentication);
        return ResponseEntity.ok("Marked as read");
    }

    @PostMapping("/{id}/ack")
    public ResponseEntity<String> markAsAcknowledged(
            @PathVariable Long id,
            Authentication authentication) {

        announcementService.markAsAcknowledged(id, authentication);
        return ResponseEntity.ok("Acknowledged");
    }
}