package com.book.review.service.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record ReviewerDto(
        Long id,
        String firstName,
        String lastName
) {
}