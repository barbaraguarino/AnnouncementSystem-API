package com.system.announcement.services;

import com.system.announcement.exceptions.CategoryIsEmptyException;
import com.system.announcement.models.Category;
import com.system.announcement.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Set<Category> getAllOrSave(@NotNull Set<String> categories){
        Set<Category> responseCategories = new HashSet<>();
        for(String category : categories){
            var optionalCategory = categoryRepository.findByName(category);
            if(optionalCategory.isPresent()){
                responseCategories.add(optionalCategory.get());
            }else{
                var newCategory = new Category();
                newCategory.setName(category);
                responseCategories.add(categoryRepository.save(newCategory));
            }
        }
        if(responseCategories.isEmpty()) throw new CategoryIsEmptyException();
        else return responseCategories;
    }

    public Set<Category> getAll() {
        return new HashSet<>(categoryRepository.findAllByOrderByName());
    }
}
