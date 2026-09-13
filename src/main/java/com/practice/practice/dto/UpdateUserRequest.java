package com.practice.practice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "Age is required") @Min(value = 0, message = "Age must be positive") Integer age,
        @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email) {
}
