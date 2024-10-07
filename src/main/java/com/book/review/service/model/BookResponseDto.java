package com.book.review.service.model;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record BookResponseDto(
        String id,
        String title,
        String author,
        String isbn,
        String publisher,
        String publishedDate,
        String description,
        List<ReviewResponseDto> reviews
) {

}
