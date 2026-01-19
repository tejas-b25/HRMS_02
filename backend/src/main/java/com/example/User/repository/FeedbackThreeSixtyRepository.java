package com.example.User.repository;

import com.example.User.entity.FeedbackThreeSixty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackThreeSixtyRepository extends JpaRepository<FeedbackThreeSixty, Long> {
    List<FeedbackThreeSixty> findByRevieweeIdAndIsDeletedFalse(Long revieweeId);
    List<FeedbackThreeSixty> findByReviewerIdAndIsDeletedFalse(Long reviewerId);
}
