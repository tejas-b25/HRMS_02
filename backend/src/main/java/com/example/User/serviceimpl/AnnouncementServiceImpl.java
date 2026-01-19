package com.example.User.serviceimpl;

import com.example.User.dto.*;
import com.example.User.entity.*;
import com.example.User.repository.*;
import com.example.User.security.SecurityService;
import com.example.User.service.AnnouncementService;
import com.example.User.util.AttachmentStorage;
import com.example.User.util.NotificationUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementReadStatusRepository readStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachmentStorage attachmentStorage;

    @Autowired
    private NotificationUtil notificationUtil;

    @Autowired
    private SecurityService securityService;

    // ================= CREATE =================
    @Override
    public AnnouncementResponseDTO createAnnouncement(
            AnnouncementRequestDTO dto,
            Authentication authentication) {

        validateAnnouncement(dto);

        User user = getLoggedInUser(authentication);

        if (!(user.getRole() == Role.HR ||
                user.getRole() == Role.ADMIN ||
                user.getRole() == Role.MANAGER)) {
            throw new RuntimeException("You are not authorized to create announcement");
        }

        Announcement announcement = new Announcement();
        mapDtoToEntity(dto, announcement, user);

        Announcement saved = announcementRepository.save(announcement);
        sendNotificationIfRequired(dto, saved);

        return mapEntityToDto(saved);
    }

    // ================= UPDATE =================
    @Override
    public AnnouncementResponseDTO updateAnnouncement(
            Long id,
            AnnouncementRequestDTO dto,
            Authentication authentication) {

        validateAnnouncement(dto);

        User user = getLoggedInUser(authentication);

        if (!(user.getRole() == Role.HR ||
                user.getRole() == Role.ADMIN ||
                user.getRole() == Role.MANAGER)) {
            throw new RuntimeException("You are not authorized to update announcement");
        }

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        mapDtoToEntity(dto, announcement, user);
        // only role in updatedBy
        announcement.setUpdatedBy(user.getRole().name());

        Announcement updated = announcementRepository.save(announcement);
        sendNotificationIfRequired(dto, updated);

        return mapEntityToDto(updated);
    }

    // ================= DELETE =================
    @Override
    public void deleteAnnouncement(Long id, boolean hardDelete) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        if (hardDelete) {
            announcementRepository.delete(announcement);
        } else {
            announcement.setIsActive(false);
            announcementRepository.save(announcement);
        }
    }

    // ================= GET =================
    @Override
    public List<AnnouncementResponseDTO> getAllAnnouncements() {
        return announcementRepository.findAll()
                .stream().map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementResponseDTO> getActiveAnnouncements() {
        return announcementRepository.findByIsActiveTrue()
                .stream().map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    // ================= READ =================
    @Override
    @Transactional
    public void markAsRead(Long announcementId, Authentication authentication) {
        User user = getLoggedInUser(authentication);
        if (user.getRole() != Role.EMPLOYEE) {
            throw new RuntimeException("Only employees can mark as read");
        }

        AnnouncementReadStatus status = readStatusRepository
                .findByAnnouncement_AnnouncementIdAndUser_Id(announcementId, user.getId())
                .orElseGet(() -> {
                    Announcement a = announcementRepository.findById(announcementId)
                            .orElseThrow(() -> new RuntimeException("Announcement not found"));
                    AnnouncementReadStatus rs = new AnnouncementReadStatus();
                    rs.setAnnouncement(a);
                    rs.setUser(user);
                    return rs;
                });

        if (status.getReadAt() == null) {
            status.setReadAt(LocalDateTime.now());
            readStatusRepository.save(status);
        }
    }

    // ================= ACK =================
    @Override
    @Transactional
    public void markAsAcknowledged(Long announcementId, Authentication authentication) {
        User user = getLoggedInUser(authentication);
        if (user.getRole() != Role.EMPLOYEE) {
            throw new RuntimeException("Only employees can acknowledge");
        }

        AnnouncementReadStatus status = readStatusRepository
                .findByAnnouncement_AnnouncementIdAndUser_Id(announcementId, user.getId())
                .orElseThrow(() -> new RuntimeException("Please read announcement first"));

        status.setAcknowledged(true);
        status.setAcknowledgedAt(LocalDateTime.now());
        readStatusRepository.save(status);
    }

    // ================= READ STATUS =================
    @Override
    public List<AnnouncementReadStatusResponseDTO> getReadStatus(Long announcementId) {
        return readStatusRepository.findByAnnouncement_AnnouncementId(announcementId)
                .stream()
                .map(this::mapReadStatusEntityToDto)
                .collect(Collectors.toList());
    }

    // ================= HELPERS =================
    private User getLoggedInUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private void mapDtoToEntity(
            AnnouncementRequestDTO dto,
            Announcement entity,
            User user) {

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setPriority(dto.getPriority());
        entity.setTargetDepartment(dto.getTargetDepartment());
        entity.setTargetLocation(dto.getTargetLocation());
        entity.setTargetRoles(dto.getTargetRoles());
        entity.setPublishDate(dto.getPublishDate());
        entity.setExpireDate(dto.getExpireDate());

        entity.setCreatedById(user.getId());
        entity.setCreatedByName(user.getFirstName() + " " +
                (user.getLastName() != null ? user.getLastName() : ""));
        entity.setCreatedByRole(user.getRole().name());

        MultipartFile file = dto.getAttachment();
        if (file != null && !file.isEmpty()) {
            try {
                String fileUrl = attachmentStorage.saveFile(file);
                entity.setAttachmentUrl(fileUrl);
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload attachment", e);
            }
        }
    }

    private void sendNotificationIfRequired(
            AnnouncementRequestDTO dto,
            Announcement saved) {

        if (dto.getEmployeeIds() == null || dto.getEmployeeIds().length == 0) return;
        if (!dto.isEmail() && !dto.isPush() && !dto.isInApp()) return;

        notificationUtil.sendNotificationToEmployees(
                dto.getEmployeeIds(),
                "New Announcement: " + saved.getTitle(),
                dto.isEmail(),
                dto.isPush(),
                dto.isInApp()
        );
    }

    private void validateAnnouncement(AnnouncementRequestDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank())
            throw new RuntimeException("Title required");
        if (dto.getDescription() == null || dto.getDescription().isBlank())
            throw new RuntimeException("Description required");

        MultipartFile file = dto.getAttachment();
        if (file != null && !file.isEmpty()) {
            String[] allowed = {"pdf","png","jpg","jpeg","doc","docx"};
            String ext = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            if (!Arrays.asList(allowed).contains(ext))
                throw new RuntimeException("Invalid file type");
        }
    }

    private AnnouncementResponseDTO mapEntityToDto(Announcement e) {
        AnnouncementResponseDTO dto = new AnnouncementResponseDTO();
        dto.setAnnouncementId(e.getAnnouncementId());
        dto.setTitle(e.getTitle());
        dto.setDescription(e.getDescription());
        dto.setCategory(e.getCategory());
        dto.setPriority(e.getPriority());
        dto.setAttachmentUrl(e.getAttachmentUrl());
        dto.setTargetDepartment(e.getTargetDepartment());
        dto.setTargetLocation(e.getTargetLocation());
        dto.setTargetRoles(e.getTargetRoles());
        dto.setPublishDate(e.getPublishDate());
        dto.setExpireDate(e.getExpireDate());
        dto.setCreatedBy(e.getCreatedByName());
        dto.setCreatedByRole(e.getCreatedByRole());
        dto.setUpdatedBy(e.getUpdatedBy()); // only role
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUpdatedAt(e.getUpdatedAt());
        dto.setIsActive(e.getIsActive());
        return dto;
    }

    private AnnouncementReadStatusResponseDTO mapReadStatusEntityToDto(AnnouncementReadStatus e) {
        AnnouncementReadStatusResponseDTO dto = new AnnouncementReadStatusResponseDTO();
        dto.setReadId(e.getReadId());
        dto.setAnnouncementId(e.getAnnouncement().getAnnouncementId());
        dto.setUserId(e.getUser().getId());
        dto.setReadAt(e.getReadAt());
        dto.setAcknowledged(e.getAcknowledged());
        dto.setAcknowledgedAt(e.getAcknowledgedAt());
        return dto;
    }
}
