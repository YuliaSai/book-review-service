package com.book.review.service.service.impl;

import com.book.review.service.client.GoogleBooksFeignClient;
import com.book.review.service.config.ApiKeyProperties;
import com.book.review.service.dao.repository.ReviewRepository;
import com.book.review.service.mapper.ReviewMapper;
import com.book.review.service.mapper.UserMapper;
import com.book.review.service.model.BookResponseDto;
import com.book.review.service.model.RequestFilterDto;
import com.book.review.service.model.ReviewResponseDto;
import com.book.review.service.service.BookService;
import com.book.review.service.util.helper.BookTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.book.review.service.util.helper.BookTransformer.buildQuery;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final GoogleBooksFeignClient googleBooksFeignClient;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserMapper userMapper;
    private final ApiKeyProperties apiKeyProperties;

    @Override
    public Page<BookResponseDto> getBooksByFilter(RequestFilterDto filters, Pageable pageable) {
        log.debug("ActionLog.getAllBooksByFilter.start");

        final var googleBooks = getBookResponseDto(filters, pageable);
        final var books = googleBooks.stream()
                .map(bookEntity -> {
                    final var reviews = getReviews(bookEntity.id());
                    return bookEntity.toBuilder().reviews(reviews).build();
                }).toList();

        log.debug("ActionLog.getAllBooksByFilter.end");
        return new PageImpl<>(books, pageable, books.size());
    }

    private List<BookResponseDto> getBookResponseDto(RequestFilterDto filters, Pageable pageable) {
        final var googleBooks = googleBooksFeignClient.getBooksByFilter(buildQuery(filters),
                pageable.getPageNumber() * pageable.getPageSize(), pageable.getPageSize(),
                null, apiKeyProperties.getKey());
        return googleBooks.getItems().stream()
                .map(BookTransformer::convertToBookResponseDto).toList();
    }

    private List<ReviewResponseDto> getReviews(String bookId) {
        return reviewRepository.findByBookId(bookId)
                .stream()
                .map(reviewEntity -> {
                    final var reviewerDto = userMapper.toReviewDto(reviewEntity.getReviewer());
                    return reviewMapper.toReviewDto(reviewEntity, reviewerDto);
                })
                .toList();
    }
}
