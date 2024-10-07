package com.book.review.service.model;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder(toBuilder = true)
public record ReviewResponseDto(
        Long id,
        String bookId,
        ReviewerDto reviewer,
        String review,
        ZonedDateTime reviewDate
) {
}
