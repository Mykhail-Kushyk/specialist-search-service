package com.ua.searchservice.entity.customer.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AuthCustomerRequest {

    @NotBlank
    @Size(min = 4, max = 15, message = "Username should be between 4 and 15")
    private String username;

    @NotBlank
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20")
    private String password;

    @Email
    private String email;
}