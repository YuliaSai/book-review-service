package com.book.review.service.service.impl;

import com.book.review.service.dao.repository.UserRepository;
import com.book.review.service.exception.AuthorisationException;
import com.book.review.service.mapper.UserMapper;
import com.book.review.service.model.UserDto;
import com.book.review.service.service.UserService;
import com.book.review.service.util.enums.Role;
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
    public UserDto findByEmail(String email) {
        log.debug("ActionLog.findByLogin.start -> Get user by email {}", email);
        final var user = userMapper.toUserDto(userRepository.findByEmail(email).orElseThrow(() ->
                new AuthorisationException(HttpStatus.NOT_FOUND,
                        String.format(AuthorisationException.NOT_FOUND_USER_BY_EMAIL, email))));

        log.debug("ActionLog.findByLogin.end -> Get user by email {}", email);
        return user;
    }

    @Override
    public void registerUser(UserDto userRegistrationDto) {
        log.debug("ActionLog.registerUser.start -> Add new user with email {}", userRegistrationDto.email());

        if (userRepository.findByEmail(userRegistrationDto.email()).isPresent()) {
            throw new AuthorisationException(HttpStatus.CONFLICT,
                    String.format(AuthorisationException.USER_ALREADY_EXISTS, userRegistrationDto.email()));
        }

        var newUser = userRegistrationDto.toBuilder()
                .role(Role.REVIEWER)
                .password(passwordEncoder.encode(userRegistrationDto.password()))
                .build();

        userRepository.save(userMapper.toUserEntity(newUser));
        log.debug("ActionLog.registerUser.end -> Add new user with email {}", userRegistrationDto.email());
    }
}
