package com.book.review.service.service.impl;

import com.book.review.service.dao.entity.UserEntity;
import com.book.review.service.dao.repository.UserRepository;
import com.book.review.service.exception.AuthorizationException;
import com.book.review.service.mapper.UserMapper;
import com.book.review.service.mapper.UserMapperImpl;
import com.book.review.service.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.book.review.service.MockData.getValidUserDto;
import static com.book.review.service.MockData.getValidUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userEntity = getValidUserEntity();
        userDto = getValidUserDto();
    }

    @Test
    void whenEmailExists_thenReturnUser() {
        // given
        final var email = userDto.email();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // when
        final var result = userService.findByEmail(email);

        // then
        assertNotNull(result);
        assertEquals(userDto, result);
    }

    @Test
    void whenEmailDoesNotExist_thenThrowNotFoundException() {
        // given
        final var email = userDto.email();
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when & then
        final var exception = assertThrows(AuthorizationException.class, () -> userService.findByEmail(email));

        assertEquals(String.format(AuthorizationException.NOT_FOUND_USER_BY_EMAIL_TEMPLATE, email),
                exception.getMessage());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void whenRegisterValidUser_thenUserIsSaved() {
        // given
        when(userRepository.findByEmail(userDto.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.password())).thenReturn("encodedPassword");

        // when
        userService.registerUser(userDto);

        // then
        verify(userRepository).save(any(UserEntity.class));
        verify(passwordEncoder).encode(userDto.password());
    }

    @Test
    void whenRegisterExistingUser_thenThrowConflictException() {
        // given
        when(userRepository.findByEmail(userDto.email())).thenReturn(Optional.of(userEntity));

        // when
        final var exception = assertThrows(AuthorizationException.class, () -> userService.registerUser(userDto));

        // then
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
        assertEquals(String.format(AuthorizationException.USER_ALREADY_EXISTS_TEMPLATE, userDto.email()),
                exception.getMessage());
    }
}
