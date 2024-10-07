package com.book.review.service.service.impl;

import com.book.review.service.client.GoogleBooksFeignClient;
import com.book.review.service.config.ApiKeyProperties;
import com.book.review.service.dao.entity.ReviewEntity;
import com.book.review.service.dao.repository.ReviewRepository;
import com.book.review.service.mapper.ReviewMapper;
import com.book.review.service.mapper.ReviewMapperImpl;
import com.book.review.service.mapper.UserMapper;
import com.book.review.service.mapper.UserMapperImpl;
import com.book.review.service.model.BookResponseDto;
import com.book.review.service.model.RequestFilterDto;
import com.book.review.service.model.google.GoogleBooksResponseDto;
import com.book.review.service.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.book.review.service.MockData.getValidBookResponseDto;
import static com.book.review.service.MockData.getValidGoogleBooksResponseDto;
import static com.book.review.service.MockData.getValidRequestFilterDto;
import static com.book.review.service.MockData.getValidReviewEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private GoogleBooksFeignClient googleBooksFeignClient;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ApiKeyProperties apiKeyProperties;

    @Spy
    private ReviewMapper reviewMapper = new ReviewMapperImpl();

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @InjectMocks
    private BookServiceImpl bookService;

    private RequestFilterDto filters;
    private ReviewEntity reviewEntity;
    private BookResponseDto bookResponseDto;
    private GoogleBooksResponseDto googleBooksResponseDto;

    @BeforeEach
    void setUp() {
        filters = getValidRequestFilterDto();
        bookResponseDto = getValidBookResponseDto();
        reviewEntity = getValidReviewEntity();
        googleBooksResponseDto = getValidGoogleBooksResponseDto();
    }

    @Test
    void whenGetAllBooksByFilter_thenReturnListOfBooks() {
        // given
        final var query = "intitle:The Great Gatsby+inauthor:F. Scott Fitzgerald+isbn:9781471173943";

        when(apiKeyProperties.getKey()).thenReturn("key");
        when(googleBooksFeignClient.getBooksByFilter(query, 0, 10, null, "key"))
                .thenReturn(googleBooksResponseDto);
        when(reviewRepository.findByBookId(any(String.class))).thenReturn(List.of(reviewEntity));
        final var pageable = PageRequest.of(0, 10);

        // when
        final var result = bookService.getBooksByFilter(filters, pageable);

        // then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(List.of(bookResponseDto), result.getContent());

        verify(googleBooksFeignClient).getBooksByFilter(query, 0, 10, null, "key");
        verify(reviewRepository).findByBookId(any(String.class));
    }
}