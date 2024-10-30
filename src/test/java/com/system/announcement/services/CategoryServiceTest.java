package com.system.announcement.services;

import com.system.announcement.models.Category;
import com.system.announcement.repositories.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Nested
    class GetAllCategoriesTest {

        @Test
        @DisplayName("Should return existing categories when they exist.")
        void shouldReturnExistingCategoriesWhenExists() {
            // Data
            Set<String> categoryNames = Set.of("Category1", "Category2");
            Category category1 = new Category();
            category1.setName("Category1");
            Category category2 = new Category();
            category2.setName("Category2");

            // Arrange
            when(categoryRepository.findByName("Category1")).thenReturn(Optional.of(category1));
            when(categoryRepository.findByName("Category2")).thenReturn(Optional.of(category2));

            // Act
            Set<Category> result = categoryService.getAllOrSave(categoryNames);

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
            assertTrue(result.contains(category1));
            assertTrue(result.contains(category2));
            verify(categoryRepository, times(1)).findByName("Category1");
            verify(categoryRepository, times(1)).findByName("Category2");
            verify(categoryRepository, never()).save(any(Category.class));
        }

        @Test
        @DisplayName("Should save new categories when they do not exist.")
        void shouldSaveNewCategoriesWhenNotExists() {
            // Data
            Set<String> categoryNames = Set.of("NewCategory1", "NewCategory2");
            Category newCategory1 = new Category();
            newCategory1.setName("NewCategory1");
            Category newCategory2 = new Category();
            newCategory2.setName("NewCategory2");

            // Arrange
            when(categoryRepository.findByName("NewCategory1")).thenReturn(Optional.empty());
            when(categoryRepository.findByName("NewCategory2")).thenReturn(Optional.empty());
            when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
                Category category = invocation.getArgument(0);
                category.setId(UUID.randomUUID()); // Simula a atribuição de um ID
                return category;
            });

            // Act
            Set<Category> result = categoryService.getAllOrSave(categoryNames);

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
            assertTrue(result.stream().anyMatch(c -> c.getName().equals("NewCategory1")));
            assertTrue(result.stream().anyMatch(c -> c.getName().equals("NewCategory2")));
            verify(categoryRepository, times(1)).findByName("NewCategory1");
            verify(categoryRepository, times(1)).findByName("NewCategory2");
            verify(categoryRepository, times(2)).save(any(Category.class));
        }
    }

}