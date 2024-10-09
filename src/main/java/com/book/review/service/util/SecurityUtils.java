package com.book.review.service.util;

import com.book.review.service.exception.AuthorizationException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class SecurityUtils {

    public static String getUsername() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new AuthorizationException(HttpStatus.UNAUTHORIZED, AuthorizationException.USER_UNAUTHORIZED);
        }
        return extractUsername(authentication.getPrincipal());
    }

    private String extractUsername(Object principal) {
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else if (principal instanceof String username) {
            return username;
        } else {
            throw new AuthorizationException(HttpStatus.UNAUTHORIZED, AuthorizationException.USER_UNAUTHORIZED);
        }
    }
}
