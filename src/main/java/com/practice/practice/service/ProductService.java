package com.practice.practice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.practice.dto.ProductRequest;
import com.practice.practice.dto.ProductResponse;
import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.Category;
import com.practice.practice.model.Product;
import com.practice.practice.repository.CategoryRepository;
import com.practice.practice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::fromEntity);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category", request.getCategoryId()));

        Product product = new Product(
                category,
                request.getName(),
                request.getPrice(),
                request.getStockQty(),
                request.getDescription());
        return ProductResponse.fromEntity(productRepository.save(product));

    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product", id));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category", request.getCategoryId()));

        product.setCategory(category);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStockQty(request.getStockQty());
        product.setDescription(request.getDescription());

        return ProductResponse.fromEntity(productRepository.save(product));

    }

    @Transactional
    public void delete(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product", id));

        productRepository.deleteById(id);

    }

}
