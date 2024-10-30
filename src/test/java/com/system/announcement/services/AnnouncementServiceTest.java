package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.Announcement.requestAnnouncementRecordDTO;
import com.system.announcement.models.*;
import com.system.announcement.repositories.AnnouncementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {

    @Mock
    private AuthDetails authDetails;
    @Mock
    private AnnouncementRepository announcementRepository;
    @Mock
    private CityService cityService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private FileService fileService;

    @InjectMocks
    private AnnouncementService announcementService;

    @Nested
    class CreateAnnouncementTest {

        @Test
        @DisplayName("Should create announcement with success.")
        void shouldCreateAnnouncementWithSuccess(){
            //Data
            var requestDTO = new requestAnnouncementRecordDTO(
                    "Title",
                    "Content",
                    "City",
                    Set.of("Category1"),
                    Set.of("path/for/image.png"),
                    150.0f
            );
            var user = new User();
            user.setEmail("test@example.com");
            var announcement = new Announcement();
            announcement.setAuthor(user);
            announcement.setId(UUID.randomUUID());
            announcement.setTitle("Title");
            announcement.setContent("Content");

            //Arrange
            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(cityService.getOrSave(requestDTO.city())).thenReturn(new City(UUID.randomUUID(), requestDTO.city()));
            when(categoryService.getAllOrSave(requestDTO.categories())).thenReturn(Set.of(new Category(UUID.randomUUID(), "Category1")));
            when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);
            when(fileService.createObjectsFile(requestDTO.paths(), announcement)).thenReturn(Set.of(new File(UUID.randomUUID(), "path/for/image.png", announcement)));


            //Act
            var responseDTO = announcementService.save(requestDTO);

            //Assert
            assertNotNull(responseDTO);
            assertEquals("Title", responseDTO.title());
            assertEquals("Content", responseDTO.content());
            verify(authDetails, times(1)).getAuthenticatedUser();
            verify(cityService, times(1)).getOrSave(requestDTO.city());
            verify(categoryService, times(1)).getAllOrSave(requestDTO.categories());
            verify(announcementRepository, times(1)).save(any(Announcement.class));
            verify(fileService, times(1)).createObjectsFile(requestDTO.paths(), announcement);
        }

        @Test
        @DisplayName("Should throw exception when saving announcement fails.")
        void shouldThrowExceptionWhenSavingAnnouncementFails() {
            // Data
            var requestDTO = new requestAnnouncementRecordDTO(
                    "Title",
                    "Content",
                    "City",
                    Set.of("Category1"),
                    Set.of("path/for/image.png"),
                    150.0f
            );
            var user = new User();
            user.setEmail("test@example.com");

            // Arrange
            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(cityService.getOrSave(requestDTO.city())).thenReturn(new City(UUID.randomUUID(), requestDTO.city()));
            when(categoryService.getAllOrSave(requestDTO.categories())).thenReturn(Set.of(new Category(UUID.randomUUID(), "Category1")));
            when(announcementRepository.save(any(Announcement.class))).thenThrow(new RuntimeException("Database error"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> {
                announcementService.save(requestDTO);
            });
            verify(authDetails, times(1)).getAuthenticatedUser();
            verify(cityService, times(1)).getOrSave(requestDTO.city());
            verify(categoryService, times(1)).getAllOrSave(requestDTO.categories());
            verify(announcementRepository, times(1)).save(any(Announcement.class));
            verify(fileService, never()).createObjectsFile(any(), any()); // Não deve ser chamado
        }

    }
}