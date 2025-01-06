package com.system.announcement.services;

import com.system.announcement.exceptions.CityNotFoundException;
import com.system.announcement.models.City;
import com.system.announcement.repositories.CityRepository;
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
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Nested
    class GetAll{

        @Test
        @DisplayName("Deve retornar todas as cidades ordenadas por nome")
        void shouldReturnAllCitiesOrderedByName() {
            // Arrange
            List<City> mockCities = List.of(
                    new City(UUID.randomUUID(), "Cidade A"),
                    new City(UUID.randomUUID(), "Cidade B")
            );
            Mockito.when(cityRepository.findAllByOrderByName()).thenReturn(mockCities);

            // Act
            Set<City> result = cityService.getAll();

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.size());
            Assertions.assertTrue(result.stream().anyMatch(city -> city.getName().equals("Cidade A")));
            Assertions.assertTrue(result.stream().anyMatch(city -> city.getName().equals("Cidade B")));

            Mockito.verify(cityRepository, Mockito.times(1)).findAllByOrderByName();
        }

        @Test
        @DisplayName("Deve retornar um conjunto vazio quando o repositório estiver vazio")
        void shouldReturnEmptySetWhenRepositoryIsEmpty() {
            // Arrange
            Mockito.when(cityRepository.findAllByOrderByName()).thenReturn(Collections.emptyList());

            // Act
            Set<City> result = cityService.getAll();

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isEmpty(), "O resultado deve ser um conjunto vazio");

            Mockito.verify(cityRepository, Mockito.times(1)).findAllByOrderByName();
        }


    }

    @Nested
    class GetById{

        @Test
        @DisplayName("Deve retornar a cidade correspondente para um ID válido")
        void shouldReturnCityForValidId() {
            // Arrange
            UUID validId = UUID.randomUUID();
            City mockCity = new City(validId, "Cidade A");
            Mockito.when(cityRepository.findById(validId)).thenReturn(Optional.of(mockCity));

            // Act
            City result = cityService.getById(validId);

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals("Cidade A", result.getName());
            Mockito.verify(cityRepository, Mockito.times(1)).findById(validId);
        }

        @Test
        @DisplayName("Deve lançar CityNotFoundException para um ID inválido")
        void shouldThrowCityNotFoundExceptionForInvalidId() {
            // Arrange
            UUID invalidId = UUID.randomUUID();
            Mockito.when(cityRepository.findById(invalidId)).thenReturn(Optional.empty());

            // Act & Assert
            Assertions.assertThrows(CityNotFoundException.class, () -> cityService.getById(invalidId));

            Mockito.verify(cityRepository, Mockito.times(1)).findById(invalidId);
        }
    }
}