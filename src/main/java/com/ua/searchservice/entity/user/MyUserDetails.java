package com.ua.searchservice.entity.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class MyUserDetails extends org.springframework.security.core.userdetails.User {

    private final User source;

    public MyUserDetails(User source, List<GrantedAuthority> authorities) {
        super(source.getEmail(),
                source.getPassword(),
                source.getStatus() == UserStatus.ACTIVE,
                true,
                true,
                true,
                authorities
        );
        this.source = source;
    }

    public User getSource() {
        return source;
    }

}