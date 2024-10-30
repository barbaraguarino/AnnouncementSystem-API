package com.system.announcement.services;

import com.system.announcement.models.Announcement;
import com.system.announcement.models.File;
import com.system.announcement.repositories.FileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    @Nested
    class CreateObjectsFileTest{

        @Test
        @DisplayName("Should create and save files successfully.")
        void shouldCreateAndSaveFilesSuccessfully() {
            // Data
            Set<String> paths = Set.of("path/to/file1.png", "path/to/file2.png");
            Announcement announcement = new Announcement();
            File file1 = new File("path/to/file1.png", announcement);
            File file2 = new File("path/to/file2.png", announcement);

            // Arrange
            when(fileRepository.save(any(File.class))).thenAnswer(invocation -> {
                File file = invocation.getArgument(0);
                file.setId(UUID.randomUUID());
                return file;
            });

            // Act
            Set<File> result = fileService.createObjectsFile(paths, announcement);

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
            assertTrue(result.stream().anyMatch(f -> f.getPath().equals("path/to/file1.png")));
            assertTrue(result.stream().anyMatch(f -> f.getPath().equals("path/to/file2.png")));
            verify(fileRepository, times(2)).save(any(File.class));
        }

        @Test
        @DisplayName("Should return empty set when no paths are provided.")
        void shouldReturnEmptySetWhenNoPathsProvided() {
            // Data
            Set<String> paths = new HashSet<>();
            Announcement announcement = new Announcement();

            // Act
            Set<File> result = fileService.createObjectsFile(paths, announcement);

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(fileRepository, never()).save(any(File.class));
        }
    }


}