package com.practice.practice.exception;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // fields that are null are left out of the JSON
public record ErrorResponse(
        int status,
        String message,
        String path,
        Instant timestamp,
        Map<String, String> errors, // only for validation errors
        String debug) {             // only in development
}
