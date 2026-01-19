package com.example.User.repository;

import com.example.User.entity.AnnouncementReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnnouncementReadStatusRepository extends JpaRepository<AnnouncementReadStatus, Long> {

    List<AnnouncementReadStatus> findByAnnouncement_AnnouncementId(Long announcementId);
    boolean existsByAnnouncement_AnnouncementIdAndUser_Id(Long announcementId, Long userId);

    // <T> Optional<T> findByAnnouncement_AnnouncementIdAndEmployeeId(Long announcementId, Long id);

    Optional<AnnouncementReadStatus>
    findByAnnouncement_AnnouncementIdAndUser_Id(Long announcementId, Long userId);
}

