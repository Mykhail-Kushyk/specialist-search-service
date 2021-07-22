package com.ua.searchservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserExceptions {

    public static ResponseStatusException userNotFound(String username) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with " + username + " username not found");
    }

    public static ResponseStatusException userNotFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
    }

    public static ResponseStatusException orderNotFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + id + " not found");
    }

    public static ResponseStatusException badCredentials() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password or email is not valid");
    }

    public static ResponseStatusException wrongPassword() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match");
    }

    public static ResponseStatusException badRequest() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong input parameters");
    }
}
