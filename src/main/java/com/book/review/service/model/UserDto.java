package com.book.review.service.model;

import com.book.review.service.util.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.book.review.service.util.constants.Constants.EMAIL_PATTERN;
import static com.book.review.service.util.constants.Constants.NAME_PATTERN;
import static com.book.review.service.util.constants.Constants.PASSWORD_PATTERN;
import static com.book.review.service.util.constants.Constants.WRONG_EMAIL_MESSAGE;
import static com.book.review.service.util.constants.Constants.WRONG_FIRST_NAME_MESSAGE;
import static com.book.review.service.util.constants.Constants.WRONG_LAST_NAME_MESSAGE;
import static com.book.review.service.util.constants.Constants.WRONG_PASSWORD_MESSAGE;

@Builder(toBuilder = true)
public record UserDto(
        Long id,

        @NotBlank(message = "First name cannot be blank or null")
        @Pattern(regexp = NAME_PATTERN, message = WRONG_FIRST_NAME_MESSAGE)
        @Schema(example = "Grzegorz")
        String firstName,

        @NotBlank(message = "Last name cannot be blank or null")
        @Pattern(regexp = NAME_PATTERN, message = WRONG_LAST_NAME_MESSAGE)
        @Schema(example = "Bzeczyszczykiewicz")
        String lastName,

        @NotBlank(message = "Email cannot be blank or null")
        @Pattern(regexp = EMAIL_PATTERN, message = WRONG_EMAIL_MESSAGE)
        @Schema(example = "username@domain.com")
        String email,

        @NotBlank(message = "Password cannot be blank or null")
        @Pattern(regexp = PASSWORD_PATTERN, message = WRONG_PASSWORD_MESSAGE)
        @Schema(example = "Password1_")
        String password,

        Role role
) {
}
