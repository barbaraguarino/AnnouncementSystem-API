package com.system.announcement.services;

import com.system.announcement.exceptions.CategoryIsEmptyException;
import com.system.announcement.exceptions.CategoryNotFoundException;
import com.system.announcement.models.Category;
import com.system.announcement.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Nested
    class GetAll{

        @Test
        @DisplayName("Deve retornar todas as categorias ordenadas por nome.")
        void shouldReturnAllCategoriesOrderedByName () {
            // Arrange
            List<Category> mockCategories = List.of(
                    new Category(UUID.randomUUID(), "Categoria A"),
                    new Category(UUID.randomUUID(), "Categoria B")
            );
            Mockito.when(categoryRepository.findAllByOrderByName()).thenReturn(mockCategories);

            // Act
            Set<Category> result = categoryService.getAll();

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.size());
            Assertions.assertTrue(result.stream().anyMatch(cat -> cat.getName().equals("Categoria A")));
            Assertions.assertTrue(result.stream().anyMatch(cat -> cat.getName().equals("Categoria B")));

            Mockito.verify(categoryRepository, Mockito.times(1)).findAllByOrderByName();
        }

        @Test
        @DisplayName("Deve retornar um conjunto vazio quando o repositório estiver vazio")
        void shouldReturnEmptySetWhenRepositoryIsEmpty() {
            // Arrange
            Mockito.when(categoryRepository.findAllByOrderByName()).thenReturn(Collections.emptyList());

            // Act
            Set<Category> result = categoryService.getAll();

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isEmpty(), "O resultado deve ser um conjunto vazio");

            Mockito.verify(categoryRepository, Mockito.times(1)).findAllByOrderByName();
        }

    }

    @Nested
    class GetAllById{

        @Test
        @DisplayName("Deve retornar as categorias correspondentes para IDs válidos")
        void shouldReturnCategoriesForValidIds() {
            // Arrange
            UUID id1 = UUID.randomUUID();
            UUID id2 = UUID.randomUUID();
            Set<UUID> ids = Set.of(id1, id2);

            Category category1 = new Category(id1, "Categoria A");
            Category category2 = new Category(id2, "Categoria B");

            Mockito.when(categoryRepository.findById(id1)).thenReturn(Optional.of(category1));
            Mockito.when(categoryRepository.findById(id2)).thenReturn(Optional.of(category2));

            // Act
            Set<Category> result = categoryService.getAllById(ids);

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.size());
            Assertions.assertTrue(result.contains(category1));
            Assertions.assertTrue(result.contains(category2));

            Mockito.verify(categoryRepository, Mockito.times(1)).findById(id1);
            Mockito.verify(categoryRepository, Mockito.times(1)).findById(id2);
        }

        @Test
        @DisplayName("Deve lançar CategoryNotFoundException para IDs inválidos")
        void shouldThrowCategoryNotFoundExceptionForInvalidId() {
            // Arrange
            UUID invalidId = UUID.randomUUID();
            Set<UUID> ids = Set.of(invalidId);

            Mockito.when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());

            // Act & Assert
            Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryService.getAllById(ids));

            Mockito.verify(categoryRepository, Mockito.times(1)).findById(invalidId);
        }

        @Test
        @DisplayName("Deve lançar CategoryIsEmptyException quando o conjunto de retorno for vazio")
        void shouldThrowCategoryIsEmptyExceptionWhenResultIsEmpty() {
            // Arrange
            Set<UUID> ids = Set.of(); // Nenhum ID fornecido

            // Act & Assert
            Assertions.assertThrows(CategoryIsEmptyException.class, () -> categoryService.getAllById(ids));

            Mockito.verifyNoInteractions(categoryRepository); // O repositório não deve ser chamado
        }

    }
}