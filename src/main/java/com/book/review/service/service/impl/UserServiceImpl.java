package com.book.review.service.service.impl;

import com.book.review.service.dao.repository.UserRepository;
import com.book.review.service.exception.AuthorizationException;
import com.book.review.service.mapper.UserMapper;
import com.book.review.service.model.UserDto;
import com.book.review.service.service.UserService;
import com.book.review.service.util.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerUser(UserDto userRegistrationDto) {
        log.debug("ActionLog.registerUser.start -> Add new user with email {}", userRegistrationDto.email());

        if (userRepository.findByEmail(userRegistrationDto.email()).isPresent()) {
            throw new AuthorizationException(HttpStatus.CONFLICT,
                    String.format(AuthorizationException.USER_ALREADY_EXISTS_TEMPLATE, userRegistrationDto.email()));
        }

        final var newUser = userRegistrationDto.toBuilder()
                .role(Role.REVIEWER)
                .password(passwordEncoder.encode(userRegistrationDto.password()))
                .build();

        userRepository.save(userMapper.toUserEntity(newUser));
        log.debug("ActionLog.registerUser.end -> Add new user with email {}", userRegistrationDto.email());
    }

    @Override
    public UserDto findByEmail(String email) {
        log.debug("ActionLog.findByLogin.start -> Get user by email {}", email);
        final var user = userMapper.toUserDto(userRepository.findByEmail(email).orElseThrow(() ->
                new AuthorizationException(HttpStatus.NOT_FOUND,
                        String.format(AuthorizationException.NOT_FOUND_USER_BY_EMAIL_TEMPLATE, email))));

        log.debug("ActionLog.findByLogin.end -> Get user by email {}", email);
        return user;
    }
}
