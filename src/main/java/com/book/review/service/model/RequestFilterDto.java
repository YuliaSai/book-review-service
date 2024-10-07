package com.book.review.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(toBuilder = true)
public record RequestFilterDto(
        @Schema(example = "_ojXNuzgHRcC") String id,
        @Schema(example = "The Great Gatsby") String title,
        @Schema(example = "F. Scott Fitzgerald") String author,
        @Schema(example = "9780451524935") String isbn
) {
}
