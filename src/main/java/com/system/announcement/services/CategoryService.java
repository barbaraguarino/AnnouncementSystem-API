package com.system.announcement.services;

import com.system.announcement.exceptions.CategoryNotFoundException;
import com.system.announcement.models.Category;
import com.system.announcement.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Set<Category> getAllById(@NotNull Set<UUID> categories) {
        var response = new HashSet<>(categoryRepository.findAllById(categories));
        if(response.size() != categories.size()) throw new CategoryNotFoundException();
        return response;
    }
}
