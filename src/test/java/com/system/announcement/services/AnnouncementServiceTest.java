package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.models.Announcement;
import com.system.announcement.repositories.AnnouncementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
    class CreateAnnouncement {

        @Test
        @DisplayName("Should create announcement with success.")
        void shouldCreateAnnouncementWithSuccess(){
            //Arrange

            //Act

            //Assert
        }
    }

}