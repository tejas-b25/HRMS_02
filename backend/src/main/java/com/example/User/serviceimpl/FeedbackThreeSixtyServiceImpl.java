package com.example.User.serviceimpl;

import com.example.User.dto.FeedbackThreeSixtyDTO;
import com.example.User.entity.FeedbackThreeSixty;
import com.example.User.repository.FeedbackThreeSixtyRepository;
import com.example.User.service.FeedbackThreeSixtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackThreeSixtyServiceImpl implements FeedbackThreeSixtyService {
    @Autowired
    private  FeedbackThreeSixtyRepository repository;
    @Override
    public FeedbackThreeSixtyDTO createFeedback(FeedbackThreeSixtyDTO dto) {
        FeedbackThreeSixty entity = new FeedbackThreeSixty();
        BeanUtils.copyProperties(dto, entity);
        FeedbackThreeSixty saved = repository.save(entity);
        FeedbackThreeSixtyDTO response = new FeedbackThreeSixtyDTO();
        BeanUtils.copyProperties(saved, response);
        return response;
    }

    @Override
    public FeedbackThreeSixtyDTO updateFeedback(Long id, FeedbackThreeSixtyDTO dto) {
        FeedbackThreeSixty existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id " + id));
        existing.setComments(dto.getComments());
        existing.setRating(dto.getRating());
        existing.setReviewerName(dto.getReviewerName());
        existing.setRevieweeName(dto.getRevieweeName());
        FeedbackThreeSixty updated = repository.save(existing);
        FeedbackThreeSixtyDTO response = new FeedbackThreeSixtyDTO();
        BeanUtils.copyProperties(updated, response);
        return response;
    }

    @Override
    public List<FeedbackThreeSixtyDTO> getAllFeedbacks() {
        return repository.findAll().stream()
                .filter(f -> !f.getIsDeleted())
                .map(f -> {
                    FeedbackThreeSixtyDTO dto = new FeedbackThreeSixtyDTO();
                    BeanUtils.copyProperties(f, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackThreeSixtyDTO getFeedbackById(Long id) {
        FeedbackThreeSixty entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id " + id));
        FeedbackThreeSixtyDTO dto = new FeedbackThreeSixtyDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public List<FeedbackThreeSixtyDTO> getFeedbacksByReviewee(Long revieweeId) {
        return repository.findByRevieweeIdAndIsDeletedFalse(revieweeId)
                .stream()
                .map(f -> {
                    FeedbackThreeSixtyDTO dto = new FeedbackThreeSixtyDTO();
                    BeanUtils.copyProperties(f, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFeedback(Long id) {
        FeedbackThreeSixty entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id " + id));
        entity.setIsDeleted(true);
        repository.save(entity);
    }
}
