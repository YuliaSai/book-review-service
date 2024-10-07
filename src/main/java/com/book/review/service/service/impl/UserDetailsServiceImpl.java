package com.book.review.service.service.impl;

import com.book.review.service.dao.entity.UserAuthenticated;
import com.book.review.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) {
        final var user = userService.findByEmail(login);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.role().getAuthority()));
        return new UserAuthenticated(user.email(), user.password(),
                grantedAuthorities, user.id(), user.firstName(), user.lastName());
    }
}
