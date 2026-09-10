package com.practice.practice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest (
   @NotBlank  String name,
   @Min(value = 0) int age,
    @NotBlank @Email String email
){}
