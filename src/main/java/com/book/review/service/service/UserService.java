package com.book.review.service.service;

import com.book.review.service.model.UserDto;

public interface UserService {

    void registerUser(UserDto userRegistrationDto);

    UserDto findByEmail(String email);
}
