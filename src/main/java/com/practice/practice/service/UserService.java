package com.practice.practice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.practice.dto.CreateUserRequest;
import com.practice.practice.dto.UpdateUserRequest;
import com.practice.practice.dto.UserResponse;
import com.practice.practice.exception.EmailAlreadyExistException;
import com.practice.practice.exception.UserNotFoundException;
import com.practice.practice.model.User;
import com.practice.practice.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponse::fromEntity).toList();
    }

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistException(request.email());
        }
        User newUser = new User(request.name(), request.age(), request.email());
        User savedUser = userRepository.save(newUser);
        return UserResponse.fromEntity(savedUser);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.fromEntity(user);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (userRepository.existsByEmailAndIdNot(request.email(),id)) {
            throw new EmailAlreadyExistException(request.email());
        }
        user.setName(request.name());
        user.setAge(request.age());
        user.setEmail(request.email());
        User updatedUser = userRepository.save(user);
        return UserResponse.fromEntity(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
