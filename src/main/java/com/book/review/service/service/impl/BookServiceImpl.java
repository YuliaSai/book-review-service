package com.book.review.service.service.impl;

import com.book.review.service.client.GoogleBooksFeignClient;
import com.book.review.service.config.ApiKeyProperties;
import com.book.review.service.model.BookResponseDto;
import com.book.review.service.model.RequestFilterDto;
import com.book.review.service.model.ReviewResponseDto;
import com.book.review.service.service.BookService;
import com.book.review.service.service.ReviewService;
import com.book.review.service.util.BookUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.book.review.service.util.BookUtils.buildQuery;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final List<ReviewResponseDto> EMPTY_REVIEW_LIST = List.of();

    private final GoogleBooksFeignClient googleBooksFeignClient;
    private final ReviewService reviewService;
    private final ApiKeyProperties apiKeyProperties;

    @Override
    public Page<BookResponseDto> getBooksByFilter(RequestFilterDto filters, Pageable pageable) {
        log.debug("ActionLog.getAllBooksByFilter.start: filters={}, pageNumber={}, pageSize={}",
                filters, pageable.getPageNumber(), pageable.getPageSize());

        final var googleBooks = getBookResponseDto(filters, pageable);

        final var bookIds = googleBooks.stream()
                .map(BookResponseDto::id)
                .toList();
        final var reviewsByBookId = reviewService.getReviewsByBookIds(bookIds);

        final var books = googleBooks.stream()
                .map(book -> {
                    final var reviews = reviewsByBookId.getOrDefault(book.id(), EMPTY_REVIEW_LIST);
                    return book.toBuilder().reviews(reviews).build();
                })
                .toList();

        log.debug("ActionLog.getAllBooksByFilter.end");
        return new PageImpl<>(books, pageable, books.size());
    }

    private List<BookResponseDto> getBookResponseDto(RequestFilterDto filters, Pageable pageable) {
        final var googleBooks = googleBooksFeignClient.getBooksByFilter(
                buildQuery(filters),
                pageable.getPageNumber() * pageable.getPageSize(),
                pageable.getPageSize(),
                null,
                apiKeyProperties.getKey());

        if (googleBooks.getItems() == null) {
            return List.of();
        }

        return googleBooks.getItems()
                .stream()
                .map(BookUtils::convertToBookResponseDto)
                .toList();
    }
}
