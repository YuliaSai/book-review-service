package com.book.review.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder(toBuilder = true)
public record ReviewRequestDto(
        @Schema(description = "Unique identifier of the book", example = "_ojXNuzgHRcC")
        @NotBlank(message = "Book id cannot be blank")
        String bookId,

        @Schema(description = "Text of the review", example = "Not my cup of tea")
        @NotBlank(message = "Review cannot be blank")
        @Size(min = 5, max = 500, message = "Review must be between 5 and 500 characters")
        String review
) {
}
