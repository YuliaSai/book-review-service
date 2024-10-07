package com.book.review.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(toBuilder = true)
public record ReviewRequestDto(
        @Schema(example = "iWA-DwAAQBAJ") String bookId,
        @Schema(example = "Not my cup of tea") String review
) {
}
