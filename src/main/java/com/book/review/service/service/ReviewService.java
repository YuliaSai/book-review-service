package com.book.review.service.service;

import com.book.review.service.model.ReviewRequestDto;
import com.book.review.service.model.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    Page<ReviewResponseDto> getReviewsByBookId(String bookId, Pageable pageable);

    Page<ReviewResponseDto> getReviewsByReviewerId(Long reviewerId, Pageable pageable);

    void addReview(ReviewRequestDto reviewRequestDto);

    void updateReview(Long id, ReviewRequestDto reviewRequestDto);

    void deleteReview(Long id, String bookId);
}
