package com.book.review.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(toBuilder = true)
public record RequestFilterDto(
        @Schema(description = "Unique identifier of the book", example = "_ojXNuzgHRcC")
        String id,
        @Schema(description = "Title of the book", example = "The Great Gatsby")
        String title,
        @Schema(description = "Author of the book", example = "F. Scott Fitzgerald")
        String author,
        @Schema(description = "ISBN number of the book", example = "9780451524935")
        String isbn
) {
}
