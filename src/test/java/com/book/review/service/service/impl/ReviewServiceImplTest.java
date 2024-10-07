package com.book.review.service.service.impl;

import com.book.review.service.client.GoogleBooksFeignClient;
import com.book.review.service.config.ApiKeyProperties;
import com.book.review.service.dao.entity.ReviewEntity;
import com.book.review.service.dao.entity.UserEntity;
import com.book.review.service.dao.repository.ReviewRepository;
import com.book.review.service.dao.repository.UserRepository;
import com.book.review.service.exception.BusinessException;
import com.book.review.service.mapper.ReviewMapper;
import com.book.review.service.mapper.ReviewMapperImpl;
import com.book.review.service.mapper.UserMapper;
import com.book.review.service.mapper.UserMapperImpl;
import com.book.review.service.model.BookResponseDto;
import com.book.review.service.model.ReviewRequestDto;
import com.book.review.service.model.google.GoogleBookItemDto;
import com.book.review.service.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static com.book.review.service.MockData.getValidBookResponseDto;
import static com.book.review.service.MockData.getValidGoogleBookItemDto;
import static com.book.review.service.MockData.getValidReviewEntity;
import static com.book.review.service.MockData.getValidReviewRequestDto;
import static com.book.review.service.MockData.getValidUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private GoogleBooksFeignClient googleBooksFeignClient;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApiKeyProperties apiKeyProperties;

    @Spy
    private ReviewMapper reviewMapper = new ReviewMapperImpl();

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private BookResponseDto bookResponseDto;
    private ReviewRequestDto reviewRequestDto;
    private ReviewEntity reviewEntity;
    private UserEntity userEntity;
    private GoogleBookItemDto googleBookItemDto;
    private int pageNo;
    private int pageSize;
    private Pageable pageable;
    private String email;

    @BeforeEach
    void setUp() {
        bookResponseDto = getValidBookResponseDto();
        reviewRequestDto = getValidReviewRequestDto();
        reviewEntity = getValidReviewEntity();
        userEntity = getValidUserEntity();
        googleBookItemDto = getValidGoogleBookItemDto();
        pageNo = 0;
        pageSize = 10;
        pageable = PageRequest.of(pageNo, pageSize, Sort.by("id"));
        email = "john.doe@example.com";

        TestingAuthenticationToken authenticationToken =
                new TestingAuthenticationToken("john.doe@example.com", "password", "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Test
    void whenGetReviewsByBookId_thenReturnListOfReviews() {
        // given
        when(reviewRepository.getReviewsByBookId(bookResponseDto.id(), pageable))
                .thenReturn(new PageImpl<>(List.of(reviewEntity)));

        // when
        final var result = reviewService.getReviewsByBookId(bookResponseDto.id(), pageable);

        // then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(reviewRepository).getReviewsByBookId(bookResponseDto.id(), pageable);
    }

    @Test
    void whenGetReviewsByReviewerId_thenReturnReviews() {
        // given
        when(reviewRepository.getReviewsByReviewerId(1L, pageable))
                .thenReturn(new PageImpl<>(List.of(reviewEntity)));

        // when
        final var result = reviewService.getReviewsByReviewerId(1L, pageable);

        // then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(reviewRepository).getReviewsByReviewerId(1L, pageable);
    }

    @Test
    void whenAddValidReview_thenSaveNewReview() {
        // given
        when(apiKeyProperties.getKey()).thenReturn("key");
        when(googleBooksFeignClient.getBookById(any(String.class), any(String.class))).thenReturn(googleBookItemDto);
        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(reviewEntity);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // when
        reviewService.addReview(reviewRequestDto);

        // then
        verify(reviewRepository).save(any(ReviewEntity.class));
    }

    @Test
    void whenGivenValidData_thenUpdateReview() {
        // given
        when(reviewRepository.getReviewByIdAndBookIdAndReviewerId(1L, bookResponseDto.id(), 1L))
                .thenReturn(Optional.of(reviewEntity));
        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(reviewEntity);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // when
        reviewService.updateReview(1L, reviewRequestDto);

        // then
        verify(reviewRepository).save(any(ReviewEntity.class));
    }

    @Test
    void whenGivenValidData_thenDeleteReview() {
        // given
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(reviewRepository.getReviewByIdAndBookIdAndReviewerId(1L, bookResponseDto.id(), 1L))
                .thenReturn(Optional.of(reviewEntity));

        // when
        reviewService.deleteReview(1L, bookResponseDto.id());

        // then
        verify(reviewRepository).delete(any(ReviewEntity.class));
    }

    @Test
    void whenGivenInvalidData_thenThrowException() {
        // given
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(reviewRepository.getReviewByIdAndBookIdAndReviewerId(1L, bookResponseDto.id(), 1L))
                .thenReturn(Optional.empty());

        // when
        final var exception = assertThrows(BusinessException.class, () ->
                reviewService.deleteReview(1L, bookResponseDto.id()));

        // then
        assertEquals(String.format(BusinessException.NOT_FOUND_REVIEW_BY_ID, 1L), exception.getMessage());
    }
}