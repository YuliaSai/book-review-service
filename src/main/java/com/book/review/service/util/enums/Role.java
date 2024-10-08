package com.book.review.service.util.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    REVIEWER;

    @Override
    public String getAuthority() {
        return name();
    }
}
