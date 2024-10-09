package com.book.review.service.service.impl;

import com.book.review.service.client.GoogleBooksFeignClient;
import com.book.review.service.config.ApiKeyProperties;
import com.book.review.service.model.BookResponseDto;
import com.book.review.service.model.RequestFilterDto;
import com.book.review.service.model.ReviewResponseDto;
import com.book.review.service.model.google.GoogleBooksResponseDto;
import com.book.review.service.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

import static com.book.review.service.MockData.getValidBookResponseDto;
import static com.book.review.service.MockData.getValidGoogleBooksResponseDto;
import static com.book.review.service.MockData.getValidRequestFilterDto;
import static com.book.review.service.MockData.getValidReviewResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private GoogleBooksFeignClient googleBooksFeignClient;

    @Mock
    private ReviewService reviewService;

    @Mock
    private ApiKeyProperties apiKeyProperties;

    @InjectMocks
    private BookServiceImpl bookService;

    private RequestFilterDto filters;
    private ReviewResponseDto reviewResponseDto;
    private BookResponseDto bookResponseDto;
    private GoogleBooksResponseDto googleBooksResponseDto;

    @BeforeEach
    void setUp() {
        filters = getValidRequestFilterDto();
        bookResponseDto = getValidBookResponseDto();
        reviewResponseDto = getValidReviewResponseDto();
        googleBooksResponseDto = getValidGoogleBooksResponseDto();
    }

    @Test
    void whenGetAllBooksByFilter_thenReturnListOfBooks() {
        // given
        final var query = "intitle:The Great Gatsby+inauthor:F. Scott Fitzgerald+isbn:9781471173943";

        when(apiKeyProperties.getKey()).thenReturn("key");
        when(googleBooksFeignClient.getBooksByFilter(query, 0, 10, null, "key"))
                .thenReturn(googleBooksResponseDto);
        when(reviewService.getReviewsByBookIds(List.of(bookResponseDto.id())))
                .thenReturn(Map.of(reviewResponseDto.bookId(), List.of(reviewResponseDto)));
        final var pageable = PageRequest.of(0, 10);

        // when
        final var result = bookService.getBooksByFilter(filters, pageable);

        // then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(List.of(bookResponseDto), result.getContent());

        verify(googleBooksFeignClient).getBooksByFilter(query, 0, 10, null, "key");
    }

    @Test
    void whenGetAllBooksByFilter_thenReturnEmptyListOfBooks() {
        // given
        final var query = "intitle:The Great Gatsby+inauthor:F. Scott Fitzgerald+isbn:9781471173943";
        final var emptyGoogleBooksResponseDto = GoogleBooksResponseDto.builder()
                .items(List.of()).build();

        when(apiKeyProperties.getKey()).thenReturn("key");
        when(googleBooksFeignClient.getBooksByFilter(query, 0, 10, null, "key"))
                .thenReturn(emptyGoogleBooksResponseDto);
        when(reviewService.getReviewsByBookIds(List.of()))
                .thenReturn(Map.of(bookResponseDto.id(), List.of(ReviewResponseDto.builder().build())));
        final var pageable = PageRequest.of(0, 10);

        // when
        final var result = bookService.getBooksByFilter(filters, pageable);

        // then
        assertTrue(result.isEmpty());
        assertEquals(List.of(), result.getContent());

        verify(googleBooksFeignClient).getBooksByFilter(query, 0, 10, null, "key");
    }
}