package com.ua.searchservice.entity.user.response;

import com.ua.searchservice.entity.user.UserStatus;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String email;

    private String username;

    private UserStatus status;
}