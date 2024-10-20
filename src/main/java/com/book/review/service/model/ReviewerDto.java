package com.book.review.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(toBuilder = true)
public record ReviewerDto(
        @Schema(description = "Unique identifier of the reviewer", example = "123")
        Long id,
        @Schema(description = "First name of the reviewer", example = "John")
        String firstName,
        @Schema(description = "Last name of the reviewer", example = "Doe")
        String lastName
) {
}