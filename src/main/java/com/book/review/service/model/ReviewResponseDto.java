package com.book.review.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder(toBuilder = true)
public record ReviewResponseDto(
        @Schema(description = "Unique identifier of the review", example = "101")
        Long id,
        @Schema(description = "Unique identifier of the book", example = "_ojXNuzgHRcC")
        String bookId,
        @Schema(description = "Reviewer details")
        ReviewerDto reviewer,
        @Schema(description = "Text of the review", example = "An insightful and fascinating book!")
        String review,
        @Schema(description = "Date when the review was made", example = "2023-10-01")
        ZonedDateTime reviewDate
) {
}
