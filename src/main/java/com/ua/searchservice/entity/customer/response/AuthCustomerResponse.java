package com.ua.searchservice.entity.customer.response;

import com.ua.searchservice.entity.user.User;
import lombok.Data;

@Data
public class AuthCustomerResponse {

    public AuthCustomerResponse(User user, String token) {
        this.token = token;
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

    private String token;

    private Long id;

    private String username;

    private String email;

}