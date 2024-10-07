package com.book.review.service.dao.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserAuthenticated extends User {

    @Getter
    private final Long id;
    private final String firstName;
    private final String lastName;


    public UserAuthenticated(String email, String password, Collection<? extends GrantedAuthority> authorities,
                             Long id, String firstName, String lastName) {
        super(email, password, authorities);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
