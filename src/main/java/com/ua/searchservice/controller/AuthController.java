package com.ua.searchservice.controller;

import com.ua.searchservice.entity.specialist.request.AuthSpecialistRequest;
import com.ua.searchservice.entity.specialist.response.AuthSpecialistResponse;
import com.ua.searchservice.entity.user.request.SignInRequest;
import com.ua.searchservice.entity.customer.request.AuthCustomerRequest;
import com.ua.searchservice.entity.customer.response.AuthCustomerResponse;
import com.ua.searchservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthCustomerResponse registerAsCustomer(@RequestBody AuthCustomerRequest request) {
        return service.loginCustomer(request);
    }

    @PostMapping("/specialists")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthSpecialistResponse registerAsSpecialist(@RequestBody AuthSpecialistRequest request) {
        return service.loginSpecialist(request);
    }

    @PostMapping("/")
    public AuthCustomerResponse signIn(@RequestBody SignInRequest request) {
        return service.signIn(request);
    }

}