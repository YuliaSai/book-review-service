package com.book.review.service.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record MessageResponseDto(
        @Schema(example = "Response message")
        String message) {
}
