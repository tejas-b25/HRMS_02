package com.example.User.controller;

import com.example.User.dto.AnnouncementRequestDTO;
import com.example.User.dto.AnnouncementResponseDTO;
import com.example.User.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin("*")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/create")
    public AnnouncementResponseDTO create(
            @ModelAttribute AnnouncementRequestDTO dto,
            Authentication authentication) {
        return announcementService.createAnnouncement(dto, authentication);
    }

    @PutMapping("/update/{id}")
    public AnnouncementResponseDTO update(
            @PathVariable Long id,
            @ModelAttribute AnnouncementRequestDTO dto,
            Authentication authentication) {
        return announcementService.updateAnnouncement(id, dto, authentication);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    public String softDeleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id, false);
        return "Announcement soft deleted";
    }

    @DeleteMapping("/hard-delete/{id}")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    public String hardDeleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id, true);
        return "Announcement hard deleted";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    public List<AnnouncementResponseDTO> getAll() {
        return announcementService.getAllAnnouncements();
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('HR','ADMIN','MANAGER')")
    public List<AnnouncementResponseDTO> getActive() {
        return announcementService.getActiveAnnouncements();
    }
}
