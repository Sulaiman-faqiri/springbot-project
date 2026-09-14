
package com.practice.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.practice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
     // Look at that! By extending JpaRepository, you instantly get methods like:
    // .save() -> to insert data
    // .findAll() -> to get all rows
    // .findById() -> to find by ID
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email,Long id);
}
