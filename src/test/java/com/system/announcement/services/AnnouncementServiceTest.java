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

    @InjectMocks
    private AnnouncementService announcementService;

    @Nested
    class CreateAnnouncementTest {

        @Test
        @DisplayName("Should create announcement with success.")
        void shouldCreateAnnouncementWithSuccess(){

        }

        @Test
        @DisplayName("Should throw exception when saving announcement fails.")
        void shouldThrowExceptionWhenSavingAnnouncementFails() {
            
        }

    }
}