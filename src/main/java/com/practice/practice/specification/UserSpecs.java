package com.practice.practice.specification;

import org.springframework.data.jpa.domain.Specification;

import com.practice.practice.model.User;

public class UserSpecs {
    public static Specification<User> nameContains(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<User> emailContains(String email) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<User> minAge(String age) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("age"), age);
    }
}
