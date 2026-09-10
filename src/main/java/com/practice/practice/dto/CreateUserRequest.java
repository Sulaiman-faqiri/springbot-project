package com.practice.practice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
    @NotBlank(message = "Name is required")
    String name,

    @Min(value = 0, message = "Age must be positive")
    int age,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email
) {}