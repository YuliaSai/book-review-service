package com.book.review.service.util.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String NAME_PATTERN = "^(?![- ])(?!.*[- ]$)[a-zA-Z\\s-]{2,30}$";
    public static final String WRONG_FIRST_NAME_MESSAGE = "Invalid characters in first name";
    public static final String WRONG_LAST_NAME_MESSAGE = "Invalid characters in last name";
    public static final String EMAIL_PATTERN =
            "^(?!.*[._-]{2})[a-zA-Z0-9](?:[a-zA-Z0-9._-]*[a-zA-Z0-9])?@[a-zA-Z0-9](?:[a-zA-Z0-9.-]*[a-zA-Z0-9])?\\" +
                    ".[a-zA-Z]{2,6}$";
    public static final String WRONG_EMAIL_MESSAGE = "Invalid email format";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!$?*()\\[\\]{}'\";:\\\\/<>," +
            "._\\-])[a-zA-Z0-9@!$?*()\\[\\]{}'\";:\\\\/<>,._\\-]{8,50}$";
    public static final String WRONG_PASSWORD_MESSAGE = "Invalid characters in password";
}
