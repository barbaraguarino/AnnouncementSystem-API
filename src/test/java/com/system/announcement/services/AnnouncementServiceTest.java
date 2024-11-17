package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.UserRole;
import com.system.announcement.auxiliary.enums.UserType;
import com.system.announcement.dtos.Announcement.requestFilterAnnouncementRecordDTO;
import com.system.announcement.dtos.Announcement.responseOneAnnouncementRecordDTO;
import com.system.announcement.infra.specifications.AnnouncementSpecification;
import com.system.announcement.models.*;
import com.system.announcement.repositories.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
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

    private requestFilterAnnouncementRecordDTO filterDTO;
    private Pageable pageable;

    @InjectMocks
    private AnnouncementService announcementService;

    @BeforeEach
    public void setUp() {
        filterDTO = new requestFilterAnnouncementRecordDTO(
                "Title",
                "content...",
                Set.of(UUID.randomUUID(), UUID.randomUUID()),
                Set.of(UUID.randomUUID(), UUID.randomUUID()),
                10.0f,
                20.0f,
                UserType.STUDENT
        );
        pageable = PageRequest.of(0, 10);
    }

    @Nested
    class FindAllWithFilter{

        @Test
        @DisplayName("Should return announcements with filter and pagination successfully.")
        void shouldReturnAnnouncementsWithFilterAndPaginationSuccessfully() {
            Announcement announcement1 = new Announcement();
            announcement1.setTitle("Title 1");
            announcement1.setContent("Content 1");
            announcement1.setAuthor(new User("teste1@id.uff.br", "Author1", UserType.STUDENT, UserRole.USER));
            Announcement announcement2 = new Announcement();
            announcement2.setTitle("Title 2");
            announcement2.setContent("Content 2");
            announcement2.setAuthor(new User("teste2@id.uff.br", "Author2", UserType.STUDENT, UserRole.USER));


            Page<Announcement> page = new PageImpl<>(Arrays.asList(announcement1, announcement2));
            when(announcementRepository.findAll(any(AnnouncementSpecification.class), eq(pageable))).thenReturn(page);

            Page<responseOneAnnouncementRecordDTO> result = announcementService.findAllWithFilter(filterDTO, pageable);

            assertNotNull(result);
            assertEquals(2, result.getTotalElements());
            assertEquals("Title 1", result.getContent().get(0).title());
            assertEquals("Title 2", result.getContent().get(1).title());

            verify(announcementRepository).findAll(any(AnnouncementSpecification.class), eq(pageable));
        }

        @Test
        @DisplayName("Should throw exception when database access fails.")
        void shouldThrowExceptionWhenDatabaseAccessFails() {
            when(announcementRepository.findAll(any(AnnouncementSpecification.class), eq(pageable)))
                    .thenThrow(new DataAccessException("Database access error") {});

            assertThrows(DataAccessException.class, () -> {
                announcementService.findAllWithFilter(filterDTO, pageable);
            });

            verify(announcementRepository).findAll(any(AnnouncementSpecification.class), eq(pageable));
        }

        @Test
        @DisplayName("Should return empty page when no announcements match the filter.")
        void shouldReturnEmptyPageWhenNoAnnouncementsMatchFilter() {
            Page<Announcement> emptyPage = new PageImpl<>(Collections.emptyList());
            when(announcementRepository.findAll(any(AnnouncementSpecification.class), eq(pageable))).thenReturn(emptyPage);

            Page<responseOneAnnouncementRecordDTO> result = announcementService.findAllWithFilter(filterDTO, pageable);

            assertNotNull(result);
            assertEquals(0, result.getTotalElements());

            verify(announcementRepository).findAll(any(AnnouncementSpecification.class), eq(pageable));
        }

        @Test
        @DisplayName("Should return empty page when filter parameters are invalid.")
        void shouldReturnEmptyPageWhenFilterParametersAreInvalid() {
            requestFilterAnnouncementRecordDTO invalidFilterDTO = new requestFilterAnnouncementRecordDTO(
                    null, null, Collections.emptySet(), Collections.emptySet(), null, null, null);

            Page<Announcement> emptyPage = new PageImpl<>(Collections.emptyList());
            when(announcementRepository.findAll(any(AnnouncementSpecification.class), eq(pageable))).thenReturn(emptyPage);

            Page<responseOneAnnouncementRecordDTO> result = announcementService.findAllWithFilter(invalidFilterDTO, pageable);

            assertNotNull(result);
            assertEquals(0, result.getTotalElements());

            verify(announcementRepository).findAll(any(AnnouncementSpecification.class), eq(pageable));
        }

    }


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