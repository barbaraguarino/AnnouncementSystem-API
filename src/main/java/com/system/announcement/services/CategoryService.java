package com.system.announcement.services;

import com.system.announcement.exceptions.CategoryIsEmptyException;
import com.system.announcement.exceptions.CategoryNotFoundException;
import com.system.announcement.models.Category;
import com.system.announcement.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAllByOrderByName();
    }

    public Set<Category> getAllById(@NotNull Set<UUID> categories) {

        Set<Category> responseCategories = new HashSet<>();

        for(UUID id : categories){
            var optionalCategory = categoryRepository.findById(id);

            if(optionalCategory.isPresent())
                responseCategories.add(optionalCategory.get());
            else
                throw new CategoryNotFoundException();
        }

        if(responseCategories.isEmpty())
            throw new CategoryIsEmptyException();

        return responseCategories;
    }
}
