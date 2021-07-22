package com.ua.searchservice.entity.user.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class SignInRequest {

    @JsonAlias({"username", "email"})
    private String username;

    private String password;
}