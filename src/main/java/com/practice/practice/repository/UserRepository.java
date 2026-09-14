
package com.practice.practice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practice.practice.model.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    // Look at that! By extending JpaRepository, you instantly get methods like:
    // .save() -> to insert data
    // .findAll() -> to get all rows
    // .findById() -> to find by ID
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("""
            SELECT u from User u
             WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%',TRIM(:name),'%')))
            AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%',TRIM(:email),'%')))
            AND (:minAge IS NULL OR u.age >= :minAge)
            """)
    Page<User> search(
            @Param("name") String name,
            @Param("email") String email,
            @Param("minAge") Integer minAge,
            Pageable pageable);
}
