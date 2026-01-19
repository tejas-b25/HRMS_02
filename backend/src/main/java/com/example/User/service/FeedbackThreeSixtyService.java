package com.example.User.service;

import com.example.User.dto.FeedbackThreeSixtyDTO;

import java.util.List;

public interface FeedbackThreeSixtyService {
    FeedbackThreeSixtyDTO createFeedback(FeedbackThreeSixtyDTO dto);

    FeedbackThreeSixtyDTO updateFeedback(Long id, FeedbackThreeSixtyDTO dto);

    List<FeedbackThreeSixtyDTO> getAllFeedbacks();

    FeedbackThreeSixtyDTO getFeedbackById(Long id);

    List<FeedbackThreeSixtyDTO> getFeedbacksByReviewee(Long revieweeId);

    void deleteFeedback(Long id);
}
