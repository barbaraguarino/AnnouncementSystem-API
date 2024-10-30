package com.system.announcement.services;

import com.system.announcement.models.City;
import com.system.announcement.repositories.CityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Nested
    class GetOrSaveTest{

        @Test
        @DisplayName("Should save new city when it does not exist.")
        void shouldSaveNewCityWhenNotExists() {
            String cityName = "New City";
            City newCity = new City(cityName);

            // Arrange
            when(cityRepository.findByName(cityName)).thenReturn(Optional.empty());
            when(cityRepository.save(any(City.class))).thenReturn(newCity);

            // Act
            City result = cityService.getOrSave(cityName);

            // Assert
            assertNotNull(result);
            assertEquals(cityName, result.getName());
            verify(cityRepository, times(1)).findByName(cityName);
            verify(cityRepository, times(1)).save(any(City.class));
        }

        @Test
        @DisplayName("Should return existing city when it exists.")
        void shouldReturnExistingCityWhenExists() {
            // Data
            String cityName = "Existing City";
            City existingCity = new City(cityName);

            // Arrange
            when(cityRepository.findByName(cityName)).thenReturn(Optional.of(existingCity));

            // Act
            City result = cityService.getOrSave(cityName);

            // Assert
            assertNotNull(result);
            assertEquals(cityName, result.getName());
            verify(cityRepository, times(1)).findByName(cityName);
            verify(cityRepository, never()).save(any(City.class));
        }

    }

}