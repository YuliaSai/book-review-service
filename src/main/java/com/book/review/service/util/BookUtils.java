package com.book.review.service.util;

import com.book.review.service.model.BookResponseDto;
import com.book.review.service.model.RequestFilterDto;
import com.book.review.service.model.google.GoogleBookItemDto;
import com.book.review.service.model.google.GoogleIndustryIdentifierDto;
import com.book.review.service.model.google.GoogleVolumeInfoDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookUtils {

    public static String buildQuery(RequestFilterDto filterDto) {
        final var query = new StringBuilder();

        if (filterDto.title() != null && !filterDto.title().isEmpty()) {
            query.append("intitle:").append(filterDto.title()).append("+");
        }

        if (filterDto.author() != null && !filterDto.author().isEmpty()) {
            query.append("inauthor:").append(filterDto.author()).append("+");
        }

        if (filterDto.isbn() != null && !filterDto.isbn().isEmpty()) {
            query.append("isbn:").append(filterDto.isbn()).append("+");
        }

        if (!query.isEmpty() && query.charAt(query.length() - 1) == '+') {
            query.deleteCharAt(query.length() - 1);
        }

        return query.toString();
    }

    public static BookResponseDto convertToBookResponseDto(GoogleBookItemDto response) {
        final var volumeInfo = response.getVolumeInfo();
        return BookResponseDto.builder()
                .id(response.getId())
                .title(volumeInfo.getTitle())
                .author(String.join(", ", volumeInfo.getAuthors()))
                .isbn(getIsbn(volumeInfo))
                .publisher(volumeInfo.getPublisher())
                .publishedDate(volumeInfo.getPublishedDate())
                .description(volumeInfo.getDescription())
                .build();
    }

    public static String getIsbn(GoogleVolumeInfoDto volumeInfo) {
        return volumeInfo.getIndustryIdentifiers().stream()
                .filter(industryIdentifier -> "ISBN_13".equals(industryIdentifier.getType()))
                .findFirst()
                .map(GoogleIndustryIdentifierDto::getIdentifier)
                .orElse(null);
    }
}
