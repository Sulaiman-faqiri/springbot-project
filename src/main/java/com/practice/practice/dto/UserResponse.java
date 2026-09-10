package com.practice.practice.dto;

import com.practice.practice.model.User;

public record UserResponse(Long id, String name, int age, String email) {

    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getEmail());
    }
}