package com.practice.practice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.practice.dto.CategoryRequest;
import com.practice.practice.exception.ApiException;
import com.practice.practice.exception.DuplicateResourceException;
import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.Category;
import com.practice.practice.repository.CategoryRepository;
import com.practice.practice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", id));
    }

    @Transactional
    public Category create(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("category", "name", request.getName());
        }

        Category category = new Category();
        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, CategoryRequest request) {
        Category category = getById(id);

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new DuplicateResourceException("category", "name", request.getName());
        }

        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = getById(id);

        if (productRepository.existsByCategoryId(id)) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "category " + category.getName() + " still has products and cannot be deleted");
        }

        categoryRepository.delete(category);
    }

}
