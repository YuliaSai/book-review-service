package com.book.review.service.util;

import com.book.review.service.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityUtilsTest {

    private SecurityContext securityContext;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void whenGetUsername_givenPrincipalIsUserDetails_shouldReturnUsername() {
        // given
        final var userDetails = mock(UserDetails.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user123");

        // when
        final var username = SecurityUtils.getUsername();

        // then
        assertThat(username).isEqualTo("user123");
    }

    @Test
    void whenGetUsername_givenPrincipalIsString_shouldReturnUsername() {
        // given
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("user123");

        // when
        final var username = SecurityUtils.getUsername();

        // then
        assertThat(username).isEqualTo("user123");
    }

    @Test
    void whenGetUsername_givenAuthenticationIsNull_shouldThrowException() {
        // given
        when(securityContext.getAuthentication()).thenReturn(null);

        // when / then
        assertThatThrownBy(SecurityUtils::getUsername)
                .isInstanceOf(AuthorizationException.class)
                .hasMessage(AuthorizationException.USER_UNAUTHORIZED)
                .extracting("httpStatus")
                .isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void whenGetUsername_givenPrincipalIsNull_shouldThrowException() {
        // given
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(null);

        // when / then
        assertThatThrownBy(SecurityUtils::getUsername)
                .isInstanceOf(AuthorizationException.class)
                .hasMessage(AuthorizationException.USER_UNAUTHORIZED)
                .extracting("httpStatus")
                .isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void whenGetUsername_givenPrincipalIsInvalidType_shouldThrowException() {
        // given
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new Object());

        // when / then
        assertThatThrownBy(SecurityUtils::getUsername)
                .isInstanceOf(AuthorizationException.class)
                .hasMessage(AuthorizationException.USER_UNAUTHORIZED)
                .extracting("httpStatus")
                .isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}