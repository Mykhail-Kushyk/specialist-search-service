package com.ua.searchservice.entity.specialist.response;

import com.ua.searchservice.entity.user.UserStatus;
import lombok.Data;

@Data
public class SpecialistResponse {

    private Long id;

    private String username;

    private String email;

    private Integer workExperience;

    private UserStatus status;
}