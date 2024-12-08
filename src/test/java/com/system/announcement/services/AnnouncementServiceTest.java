package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.UserRole;
import com.system.announcement.auxiliary.enums.UserType;
import com.system.announcement.dtos.Announcement.requestFilterAnnouncementRecordDTO;
import com.system.announcement.dtos.Announcement.AnnouncementDTO;
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
import org.springframework.data.domain.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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
            announcement1.setDate(Timestamp.valueOf(LocalDateTime.now().minusDays(1))); // Anúncio mais antigo
            announcement1.setAuthor(new User("teste1@id.uff.br", "Author1", UserType.STUDENT, UserRole.USER));

            Announcement announcement2 = new Announcement();
            announcement2.setTitle("Title 2");
            announcement2.setContent("Content 2");
            announcement2.setDate(Timestamp.valueOf(LocalDateTime.now()));  // Anúncio mais recente
            announcement2.setAuthor(new User("teste2@id.uff.br", "Author2", UserType.STUDENT, UserRole.USER));

            List<Announcement> announcements = Arrays.asList(announcement1, announcement2);

            announcements.sort(Comparator.comparing(Announcement::getDate).reversed());

            Page<Announcement> page = new PageImpl<>(announcements);

            when(announcementRepository.findAll(any(AnnouncementSpecification.class), any(PageRequest.class)))
                    .thenReturn(page);

            Page<AnnouncementDTO> result = announcementService.findAllWithFilter(filterDTO, pageable);

            assertNotNull(result);
            assertEquals(2, result.getTotalElements());

            assertEquals("Title 2", result.getContent().get(0).title());
            assertEquals("Title 1", result.getContent().get(1).title());

            verify(announcementRepository).findAll(any(AnnouncementSpecification.class), any(PageRequest.class));
        }

        @Test
        @DisplayName("Should return all announcements without filter and pagination successfully.")
        void shouldReturnAllAnnouncementsWithoutFilterAndPaginationSuccessfully() {
            Announcement announcement1 = new Announcement();
            announcement1.setTitle("Title 1");
            announcement1.setContent("Content 1");
            announcement1.setDate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
            announcement1.setAuthor(new User("teste1@id.uff.br", "Author1", UserType.STUDENT, UserRole.USER));

            Announcement announcement2 = new Announcement();
            announcement2.setTitle("Title 2");
            announcement2.setContent("Content 2");
            announcement2.setDate(Timestamp.valueOf(LocalDateTime.now()));
            announcement2.setAuthor(new User("teste2@id.uff.br", "Author2", UserType.STUDENT, UserRole.USER));

            List<Announcement> announcements = Arrays.asList(announcement1, announcement2);

            announcements.sort(Comparator.comparing(Announcement::getDate).reversed());

            Page<Announcement> page = new PageImpl<>(announcements);

            lenient().when(announcementRepository.findAll(any(AnnouncementSpecification.class), any(PageRequest.class)))
                    .thenReturn(page);

            Page<AnnouncementDTO> result = announcementService.findAllWithFilter(null, pageable);

            assertNotNull(result);
            assertEquals(2, result.getTotalElements());

            assertEquals("Title 2", result.getContent().get(0).title());  // O mais recente (Title 2) deve ser o primeiro
            assertEquals("Title 1", result.getContent().get(1).title());  // O mais antigo (Title 1) vem depois

            verify(announcementRepository).findAll(any(AnnouncementSpecification.class), any(PageRequest.class));  // Chamando com o filtro null
        }

        @Test
        @DisplayName("Should return no announcements when no matching data is found.")
        void shouldReturnNoAnnouncementsWhenNoMatchingDataIsFound() {
            lenient().when(announcementRepository.findAll(any(AnnouncementSpecification.class), any(PageRequest.class)))
                    .thenReturn(Page.empty());

            Page<AnnouncementDTO> result = announcementService.findAllWithFilter(filterDTO, pageable);

            assertNotNull(result);
            assertEquals(0, result.getTotalElements());

            verify(announcementRepository).findAll(any(AnnouncementSpecification.class), any(PageRequest.class));  // Verifica se a chamada ao repositório foi feita
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