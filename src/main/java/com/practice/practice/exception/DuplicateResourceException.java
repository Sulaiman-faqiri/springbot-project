package com.practice.practice.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends ApiException {
    public DuplicateResourceException(String resource, String field, Object value) {
        super(HttpStatus.CONFLICT, resource + " with " + field + " " + value + " already exists");
    }
}
