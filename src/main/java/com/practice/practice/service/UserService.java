package com.practice.practice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.practice.model.User;
import com.practice.practice.repository.UserRepository;

@Service
public class UserService {
     private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

  public User createUser(String name, int age, String email) {
        User newUser = new User(name, age, email);
        return userRepository.save(newUser); 
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(); // This runs a "SELECT * FROM user" query!
    }

    public User getUserById(String id) {
        Long userId = Long.parseLong(id);
        return userRepository.findById(userId).orElse(null);
    }
    public String updateUser(Long id, String name, int age, String email){
        User user = userRepository.findById(id).orElse(null);
        if (user!=null){
            user.setName(name);
            user.setAge(age);
            user.setEmail(email);
            userRepository.save(user);
        }
        return null;
    }
}
