package com.book.review.service.config;

import com.book.review.service.exception.AuthorisationException;
import com.book.review.service.exception.BusinessException;
import com.book.review.service.model.MessageResponseDto;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MessageResponseDto> handleBusinessException(BusinessException ex) {
        log.warn("{ActionLog.handleBusinessException} -> {}", ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus()).body(new MessageResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(AuthorisationException.class)
    public ResponseEntity<MessageResponseDto> handleAuthorisationException(AuthorisationException ex) {
        log.warn("{ActionLog.handleAuthorisationException} -> {}", ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus()).body(new MessageResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(final FeignException ex) {
        log.warn("{handleFeignException} -> {}", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(HttpStatus.valueOf(ex.status()))
                .headers(headers)
                .body(ex.contentUTF8());
    }
}
