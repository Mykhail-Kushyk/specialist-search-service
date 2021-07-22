package com.ua.searchservice.entity.specialist.response;

import com.ua.searchservice.entity.specialist.Specialist;
import com.ua.searchservice.entity.user.UserStatus;
import lombok.Data;

@Data
public class AuthSpecialistResponse {

    private String token;

    private String username;

    private String email;

    private Integer workExperience;

    private UserStatus status;

    public AuthSpecialistResponse(Specialist specialist, String token) {
        this.token = token;
        this.email = specialist.getEmail();
        this.username = specialist.getUsername();
        this.status = specialist.getStatus();
        this.workExperience = specialist.getWorkExperience();
    }
}