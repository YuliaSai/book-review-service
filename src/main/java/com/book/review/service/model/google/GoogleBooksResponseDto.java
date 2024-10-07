package com.book.review.service.model.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBooksResponseDto {
    private String kind;
    private int totalItems;
    private List<GoogleBookItemDto> items;
}

