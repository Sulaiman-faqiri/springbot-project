package com.practice.practice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
    }
}
