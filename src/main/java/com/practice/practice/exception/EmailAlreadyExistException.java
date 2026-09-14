package com.practice.practice.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistException extends ApiException {

    public EmailAlreadyExistException(String email) {
        super(HttpStatus.CONFLICT, "User with email " + email + " already exists");
    }

}
