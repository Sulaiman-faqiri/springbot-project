package com.practice.practice.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.practice.practice.dto.CreateUserRequest;
import com.practice.practice.dto.UpdateUserRequest;
import com.practice.practice.dto.UserResponse;
import com.practice.practice.exception.DuplicateResourceException;
import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.User;
import com.practice.practice.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Page<UserResponse> getAllUsers(String name,Pageable pageable) {
        Page<User> users;
        if(name==null||name.isBlank()){
            users=userRepository.findAll(pageable);
        }else{
            users=userRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return users.map(UserResponse::fromEntity);
    }
   

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("User", "email", request.email());
        }
        User newUser = new User(request.name(), request.age(), request.email());
        User savedUser = userRepository.save(newUser);
        return UserResponse.fromEntity(savedUser);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
        return UserResponse.fromEntity(user);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
        if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new DuplicateResourceException("User", "email", request.email());
        }
        user.setName(request.name());
        user.setAge(request.age());
        user.setEmail(request.email());
        User updatedUser = userRepository.save(user);
        return UserResponse.fromEntity(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(id);
    }
}
