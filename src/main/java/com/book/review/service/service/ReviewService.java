package com.book.review.service.service;

import com.book.review.service.model.ReviewRequestDto;
import com.book.review.service.model.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ReviewService {

    Page<ReviewResponseDto> getReviewsByBookId(String bookId, Pageable pageable);

    Page<ReviewResponseDto> getReviewsByReviewerId(Long reviewerId, Pageable pageable);

    void addReview(ReviewRequestDto reviewRequestDto);

    void updateReview(Long id, ReviewRequestDto reviewRequestDto);

    void deleteReview(Long id, String bookId);

    Map<String, List<ReviewResponseDto>> getReviewsByBookIds(List<String> bookIds);
}
