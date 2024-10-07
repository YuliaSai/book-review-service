package com.book.review.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    public static final String NOT_FOUND_REVIEW_BY_ID = "Cannot find the review with id %s";

    private final HttpStatus httpStatus;


    public BusinessException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
