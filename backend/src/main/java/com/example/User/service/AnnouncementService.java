package com.example.User.service;

import com.example.User.dto.*;
import org.springframework.security.core.Authentication;
import java.util.List;

public interface AnnouncementService {

    AnnouncementResponseDTO createAnnouncement(
            AnnouncementRequestDTO dto,
            Authentication authentication);

    AnnouncementResponseDTO updateAnnouncement(
            Long id,
            AnnouncementRequestDTO dto,
            Authentication authentication);

    void deleteAnnouncement(Long id, boolean hardDelete);

    List<AnnouncementResponseDTO> getAllAnnouncements();
    List<AnnouncementResponseDTO> getActiveAnnouncements();

    void markAsRead(Long announcementId, Authentication authentication);
    void markAsAcknowledged(Long announcementId, Authentication authentication);
    List<AnnouncementReadStatusResponseDTO> getReadStatus(Long announcementId);
}
