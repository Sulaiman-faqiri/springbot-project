package com.practice.practice.exception;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // false unless turned on (app.errors.debug=true in application-dev.properties)
    @Value("${app.errors.debug:false}")
    private boolean debugEnabled;

    // Our own errors: UserNotFoundException, and any future ApiException
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApi(ApiException ex, HttpServletRequest req) {
        return build(ex.getStatus(), ex.getMessage(), null, ex, req);
    }

    // @Valid failed
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
            HttpServletRequest req) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> fieldErrors.put(e.getField(), e.getDefaultMessage()));
        return build(HttpStatus.BAD_REQUEST, "Validation failed", fieldErrors, ex, req);
    }

    // Broken JSON, or wrong type in the body ("age": "abc")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadJson(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, "Request body is invalid or malformed", null, ex, req);
    }

    // Wrong type in the URL (/users/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
            HttpServletRequest req) {
        String msg = "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'";
        return build(HttpStatus.BAD_REQUEST, msg, null, ex, req);
    }

    // Wrong HTTP method (e.g. PATCH on an endpoint that only supports PUT)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethod(HttpRequestMethodNotSupportedException ex,
            HttpServletRequest req) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, "Method " + ex.getMethod() + " is not supported here", null,
                ex, req);
    }

    // URL doesn't exist
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoRoute(NoResourceFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, "Endpoint not found", null, ex, req);
    }

    // Anything else = a bug on our side
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest req) {
        log.error("Unexpected error on {} {}", req.getMethod(), req.getRequestURI(), ex); // full trace in console
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", null, ex, req);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message, Map<String, String> errors,
            Exception ex, HttpServletRequest req) {
        if (status.is4xxClientError()) {
            log.warn("{} {} -> {}: {}", req.getMethod(), req.getRequestURI(), status.value(), message);
        }
        ErrorResponse body = new ErrorResponse(status.value(), message, req.getRequestURI(), Instant.now(),
                errors, debugEnabled ? debugInfo(ex) : null);
        return ResponseEntity.status(status).body(body);
    }

    // e.g. "NullPointerException: name is null (at UserService.java:38)"
    private String debugInfo(Exception ex) {
        Throwable root = ex;
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }
        String location = Arrays.stream(root.getStackTrace())
                .filter(el -> el.getClassName().startsWith("com.practice"))
                .findFirst()
                .map(el -> " (at " + el.getFileName() + ":" + el.getLineNumber() + ")")
                .orElse("");
        return root.getClass().getSimpleName() + ": " + root.getMessage() + location;
    }
}
