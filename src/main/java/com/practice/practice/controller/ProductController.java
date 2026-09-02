package com.practice.practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.practice.model.Product;


@RestController
public class ProductController {
    @GetMapping("/products")
    public Product product() {
        Product p = new Product("Laptop", 999.99);
        return p;
    }
    
}
