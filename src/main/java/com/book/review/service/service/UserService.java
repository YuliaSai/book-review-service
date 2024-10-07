package com.book.review.service.service;

import com.book.review.service.model.UserDto;

public interface UserService {

    UserDto findByEmail(String email);

    void registerUser(UserDto userRegistrationDto);
}
