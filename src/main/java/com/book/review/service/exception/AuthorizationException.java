package com.book.review.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationException extends RuntimeException {

    public static final String NOT_FOUND_USER_BY_EMAIL_TEMPLATE = "Cannot find the user with login %s";
    public static final String USER_ALREADY_EXISTS_TEMPLATE = "User with this email %s already exists";
    public static final String USER_UNAUTHORIZED = "User not authenticated";

    private final HttpStatus httpStatus;


    public AuthorizationException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
