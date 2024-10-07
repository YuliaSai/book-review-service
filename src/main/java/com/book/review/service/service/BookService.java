package com.book.review.service.service;

import com.book.review.service.model.BookResponseDto;
import com.book.review.service.model.RequestFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookResponseDto> getBooksByFilter(RequestFilterDto filters, Pageable pageable);
}