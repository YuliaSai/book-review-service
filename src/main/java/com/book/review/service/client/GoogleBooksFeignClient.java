package com.book.review.service.client;

import com.book.review.service.model.google.GoogleBookItemDto;
import com.book.review.service.model.google.GoogleBooksResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "google-books-service", url = "${google.api.host}${google.api.url}")
public interface GoogleBooksFeignClient {

    @GetMapping
    GoogleBooksResponseDto getBooksByFilter(
            @RequestParam("q") String query,
            @RequestParam("startIndex") int startIndex,
            @RequestParam("maxResults") int maxResults,
            @RequestParam(value = "orderBy", defaultValue = "newest") String orderBy,
            @RequestParam("key") String apiKey
    );

    @GetMapping("/{volumeId}")
    GoogleBookItemDto getBookById(
            @PathVariable("volumeId") String bookId,
            @RequestParam("key") String apiKey
    );
}
