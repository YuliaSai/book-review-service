package com.book.review.service;

import com.book.review.service.dao.entity.ReviewEntity;
import com.book.review.service.dao.entity.UserEntity;
import com.book.review.service.model.BookResponseDto;
import com.book.review.service.model.RequestFilterDto;
import com.book.review.service.model.ReviewRequestDto;
import com.book.review.service.model.ReviewResponseDto;
import com.book.review.service.model.ReviewerDto;
import com.book.review.service.model.UserDto;
import com.book.review.service.model.google.GoogleBookItemDto;
import com.book.review.service.model.google.GoogleBooksResponseDto;
import com.book.review.service.model.google.GoogleIndustryIdentifierDto;
import com.book.review.service.model.google.GoogleVolumeInfoDto;
import com.book.review.service.util.enums.Role;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class MockData {

    public static final String BASE_BOOKS_URL = "/api/v1/books";
    public static final String BASE_REVIEWS_URL = "/api/v1/reviews";
    public static final String REVIEWS_WITH_ID_URL = BASE_REVIEWS_URL + "/2";
    public static final String AUTH_REGISTER_URL = "/api/v1/auth/register";

    public static GoogleBooksResponseDto getValidGoogleBooksResponseDto() {
        return GoogleBooksResponseDto.builder()
                .totalItems(1)
                .items(List.of(getValidGoogleBookItemDto()))
                .build();
    }

    public static GoogleBookItemDto getValidGoogleBookItemDto() {
        return GoogleBookItemDto.builder()
                .id("iWA-DwAAQBAJ")
                .volumeInfo(getValidGoogleVolumeInfoDto())
                .build();
    }

    public static GoogleVolumeInfoDto getValidGoogleVolumeInfoDto() {
        return GoogleVolumeInfoDto.builder()
                .title("The Great Gatsby")
                .authors(List.of("F. Scott Fitzgerald"))
                .publisher("Scribner")
                .publishedDate("2005-11-15")
                .description("Some text")
                .industryIdentifiers(List.of(GoogleIndustryIdentifierDto.builder()
                        .type("ISBN_13")
                        .identifier("9780743273565")
                        .build()))
                .build();
    }

    public static RequestFilterDto getValidRequestFilterDto() {
        return RequestFilterDto.builder()
                .id("iWA-DwAAQBAJ")
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .isbn("9781471173943")
                .build();
    }

    public static BookResponseDto getValidBookResponseDto() {
        return BookResponseDto.builder()
                .id("iWA-DwAAQBAJ")
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .isbn("9780743273565")
                .publisher("Scribner")
                .publishedDate("2005-11-15")
                .description("Some text")
                .reviews(List.of(getValidReviewResponseDto()))
                .build();
    }

    public static ReviewResponseDto getValidReviewResponseDto() {
        return ReviewResponseDto.builder()
                .id(1L)
                .bookId("iWA-DwAAQBAJ")
                .reviewer(getValidReviewerDto())
                .review("An impressive work of art.")
                .reviewDate(ZonedDateTime.of(2024, 3, 28, 0, 0, 0, 0, ZoneId.systemDefault()))
                .build();
    }

    public static ReviewRequestDto getValidReviewRequestDto() {
        return ReviewRequestDto.builder()
                .bookId("iWA-DwAAQBAJ")
                .review("An impressive work of art.")
                .build();
    }

    public static ReviewEntity getValidReviewEntity() {
        return ReviewEntity.builder()
                .id(1L)
                .bookId("iWA-DwAAQBAJ")
                .reviewer(getValidUserEntity())
                .review("An impressive work of art.")
                .reviewDate(ZonedDateTime.of(2024, 3, 28, 0, 0, 0, 0, ZoneId.systemDefault()))
                .createdAt(ZonedDateTime.of(2024, 3, 28, 0, 0, 0, 0, ZoneId.systemDefault()))
                .updateAt(ZonedDateTime.of(2024, 3, 28, 0, 0, 0, 0, ZoneId.systemDefault()))
                .build();
    }

    public static ReviewerDto getValidReviewerDto() {
        return ReviewerDto.builder()
                .id(1L)
                .firstName("David")
                .lastName("Smith")
                .build();
    }

    public static UserDto getValidUserDto() {
        return UserDto.builder()
                .id(1L)
                .firstName("David")
                .lastName("Smith")
                .email("email")
                .password("password")
                .role(Role.REVIEWER)
                .build();
    }

    public static UserEntity getValidUserEntity() {
        return UserEntity.builder()
                .id(1L)
                .firstName("David")
                .lastName("Smith")
                .email("email")
                .password("password")
                .role(Role.REVIEWER)
                .createdAt(ZonedDateTime.of(2024, 3, 28, 0, 0, 0, 0, ZoneId.systemDefault()))
                .updateAt(ZonedDateTime.of(2024, 3, 28, 0, 0, 0, 0, ZoneId.systemDefault()))
                .build();
    }
}
