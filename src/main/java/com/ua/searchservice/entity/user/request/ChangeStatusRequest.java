package com.ua.searchservice.entity.user.request;

import com.ua.searchservice.entity.user.UserStatus;
import lombok.Data;

@Data
public class ChangeStatusRequest {

    private UserStatus status;
}