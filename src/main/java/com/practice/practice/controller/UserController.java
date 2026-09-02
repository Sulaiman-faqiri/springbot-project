package com.practice.practice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.practice.model.User;
import com.practice.practice.service.UserService;



@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public User createUser( @RequestParam String name, @RequestParam int age, @RequestParam String email
    ) {
        return userService.createUser(name, age, email);
    }

    @PutMapping("/{id}")
    public String editUser(@PathVariable String id, @RequestParam String name, @RequestParam int age, @RequestParam String email) {
        userService.updateUser(Long.parseLong(id), name, age, email);
        return "User updated successfully.";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        // Implement the logic to delete a user by ID
        return "User with ID " + id + " deleted successfully.";
    }


}
