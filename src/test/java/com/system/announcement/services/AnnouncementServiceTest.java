package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.auxiliary.enums.UserRole;
import com.system.announcement.auxiliary.enums.UserType;
import com.system.announcement.dtos.announcement.AnnouncementDTO;
import com.system.announcement.dtos.announcement.FilterAnnouncementDTO;
import com.system.announcement.dtos.announcement.SaveAnnouncementDTO;
import com.system.announcement.exceptions.*;
import com.system.announcement.infra.specifications.AnnouncementSpecification;
import com.system.announcement.models.Announcement;
import com.system.announcement.models.Chat;
import com.system.announcement.models.City;
import com.system.announcement.models.User;
import com.system.announcement.repositories.AnnouncementRepository;
import com.system.announcement.repositories.ChatRepository;
import com.system.announcement.repositories.FavoriteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

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
    private FavoriteRepository favoriteRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AnnouncementService announcementService;

    @Nested
    class Save{

        @Test
        @DisplayName("Deve salvar um anúncio com sucesso")
        void shouldSaveAnnouncementSuccessfully() {
            SaveAnnouncementDTO requestDTO = new SaveAnnouncementDTO(
                    "Title",
                    "Content",
                    UUID.randomUUID(),
                    Set.of(UUID.randomUUID()),
                    null,
                    100.0f
            );

            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            Mockito.when(cityService.getById(Mockito.any(UUID.class))).thenReturn(new City());
            Mockito.when(categoryService.getAllById(Mockito.anySet())).thenReturn(new HashSet<>());

            Announcement mockAnnouncement = new Announcement();
            mockAnnouncement.setAuthor(authenticatedUser);
            Mockito.when(announcementRepository.save(Mockito.any(Announcement.class))).thenReturn(mockAnnouncement);

            AnnouncementDTO savedAnnouncement = announcementService.save(requestDTO);

            Assertions.assertNotNull(savedAnnouncement);
            Mockito.verify(announcementRepository, Mockito.times(1)).save(Mockito.any(Announcement.class));
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando a cidade não for encontrada")
        void shouldThrowExceptionWhenCityNotFound() {
            SaveAnnouncementDTO requestDTO = new SaveAnnouncementDTO(
                    "Title",
                    "Content",
                    UUID.randomUUID(),
                    Set.of(UUID.randomUUID()),
                    null,
                    100.0f
            );
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);
            Mockito.when(cityService.getById(Mockito.any(UUID.class))).thenThrow(new CityNotFoundException());

            Assertions.assertThrows(CityNotFoundException.class, () -> announcementService.save(requestDTO));
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando a categoria não for encontrada")
        void shouldThrowExceptionWhenCategoryNotFound() {
            SaveAnnouncementDTO requestDTO = new SaveAnnouncementDTO(
                    "Title",
                    "Content",
                    UUID.randomUUID(),
                    Set.of(UUID.randomUUID()),
                    null,
                    100.0f
            );
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);
            Mockito.when(cityService.getById(Mockito.any(UUID.class))).thenReturn(new City());
            Mockito.when(categoryService.getAllById(Mockito.anySet())).thenThrow(new CategoryNotFoundException());

            Assertions.assertThrows(CategoryNotFoundException.class, () -> announcementService.save(requestDTO));
        }

    }

    @Nested
    class FindAllWithFilter{

        @Test
        @DisplayName("Deve retornar anúncios com filtro de título e cidade")
        void shouldReturnAnnouncementsWithTitleAndCityFilter() {
            UUID cityId = UUID.randomUUID();
            String titleFilter = "carro";
            FilterAnnouncementDTO filterDTO = new FilterAnnouncementDTO(
                    titleFilter,
                    null,
                    Set.of(cityId),
                    null,
                    null,
                    null,
                    null
            );

            Announcement announcement = new Announcement();
            announcement.setId(UUID.randomUUID());
            announcement.setTitle("carro usado");
            announcement.setContent("Anúncio de carro usado");
            announcement.setCity(new City(cityId, "Cidade Exemplo"));
            announcement.setStatus(AnnouncementStatus.VISIBLE);

            User author = new User("usuario@example.com", "Nome do Usuário", UserType.EMPLOYEE, UserRole.USER);
            announcement.setAuthor(author);

            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("date")));
            Page<Announcement> page = new PageImpl<>(List.of(announcement), pageable, 1);

            Mockito.lenient().when(announcementRepository.findAll(
                            Mockito.any(AnnouncementSpecification.class),
                            Mockito.eq(pageable)))
                    .thenReturn(page);

            Page<AnnouncementDTO> result = announcementService.findAllWithFilter(filterDTO, pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.getTotalElements());
            Assertions.assertEquals(announcement.getTitle(), result.getContent().getFirst().title());
            Mockito.verify(announcementRepository, Mockito.times(1))
                    .findAll(Mockito.any(AnnouncementSpecification.class), Mockito.eq(pageable));
        }

        @Test
        @DisplayName("Deve retornar uma página vazia quando nenhum anúncio atender aos filtros")
        void shouldReturnEmptyPageWhenNoAnnouncementsMatchFilter() {
            UUID cityId = UUID.randomUUID();
            String titleFilter = "laptop";
            FilterAnnouncementDTO filterDTO = new FilterAnnouncementDTO(
                    titleFilter,
                    null,
                    Set.of(cityId),
                    null,
                    null,
                    null,
                    null
            );

            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("date")));
            Page<Announcement> page = new PageImpl<>(Collections.emptyList(), pageable, 0);

            Mockito.lenient().when(announcementRepository.findAll(
                            Mockito.any(AnnouncementSpecification.class),
                            Mockito.eq(pageable)))
                    .thenReturn(page);

            Page<AnnouncementDTO> result = announcementService.findAllWithFilter(filterDTO, pageable);

            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isEmpty());
            Mockito.verify(announcementRepository, Mockito.times(1))
                    .findAll(Mockito.any(AnnouncementSpecification.class), Mockito.eq(pageable));
        }

    }

    @Nested
    class FindById{

        @Test
        @DisplayName("Deve retornar o anúncio quando encontrado pelo ID")
        void shouldReturnAnnouncementWhenFoundById() {
            UUID announcementId = UUID.randomUUID();
            Announcement announcement = new Announcement();
            announcement.setId(announcementId);
            announcement.setTitle("Title");
            announcement.setContent("Content");

            User author = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            announcement.setAuthor(author);

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(announcement));

            AnnouncementDTO result = announcementService.findById(announcementId);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(announcementId, result.id());
            Assertions.assertEquals("user@example.com", result.author().email());
            Mockito.verify(announcementRepository, Mockito.times(1)).findById(announcementId);
        }

        @Test
        @DisplayName("Deve lançar AnnouncementNotFoundException quando o ID não for encontrado")
        void shouldThrowAnnouncementNotFoundExceptionWhenNotFoundById() {
            UUID announcementId = UUID.randomUUID();

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.empty());

            Assertions.assertThrows(AnnouncementNotFoundException.class, () ->
                announcementService.findById(announcementId)
            );

            Mockito.verify(announcementRepository, Mockito.times(1)).findById(announcementId);
        }

    }

    @Nested
    class FindAllClosed{

        @Test
        @DisplayName("Deve retornar anúncios fechados com sucesso")
        void shouldReturnClosedAnnouncementsSuccessfully() {
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            UUID announcementId = UUID.randomUUID();
            Announcement announcement = new Announcement();
            announcement.setId(announcementId);
            announcement.setTitle("Title");
            announcement.setStatus(AnnouncementStatus.CLOSED);
            announcement.setAuthor(authenticatedUser);

            Pageable pageable = PageRequest.of(0, 5);

            Page<Announcement> announcementsPage = new PageImpl<>(List.of(announcement), pageable, 1);
            Mockito.when(announcementRepository.findAllByAuthorAndStatus(Mockito.any(User.class),
                            Mockito.eq(AnnouncementStatus.CLOSED), Mockito.any(Pageable.class)))
                    .thenReturn(announcementsPage);

            Page<AnnouncementDTO> result = announcementService.findAllClosed(pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.getTotalElements());
            Assertions.assertEquals("Title", result.getContent().getFirst().title());
            Mockito.verify(announcementRepository, Mockito.times(1)).findAllByAuthorAndStatus(Mockito.any(User.class),
                    Mockito.eq(AnnouncementStatus.CLOSED), Mockito.any(Pageable.class));
        }

        @Test
        @DisplayName("Não deve retornar anúncios se nenhum anúncio fechado for encontrado")
        void shouldReturnEmptyPageIfNoClosedAnnouncements() {
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            Pageable pageable = PageRequest.of(0, 5);

            Page<Announcement> emptyPage = Page.empty();
            Mockito.when(announcementRepository.findAllByAuthorAndStatus(Mockito.any(User.class),
                            Mockito.eq(AnnouncementStatus.CLOSED), Mockito.any(Pageable.class)))
                    .thenReturn(emptyPage);

            Page<AnnouncementDTO> result = announcementService.findAllClosed(pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(0, result.getTotalElements());
            Mockito.verify(announcementRepository, Mockito.times(1)).findAllByAuthorAndStatus(Mockito.any(User.class),
                    Mockito.eq(AnnouncementStatus.CLOSED), Mockito.any(Pageable.class));
        }
    }

    @Nested
    class FindAllSuspended{

        @Test
        @DisplayName("Deve retornar anúncios suspensos com sucesso")
        void shouldReturnSuspendedAnnouncementsSuccessfully() {
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            UUID announcementId = UUID.randomUUID();
            Announcement announcement = new Announcement();
            announcement.setId(announcementId);
            announcement.setTitle("Suspended Title");
            announcement.setStatus(AnnouncementStatus.SUSPENDED);
            announcement.setAuthor(authenticatedUser);

            Pageable pageable = PageRequest.of(0, 5);

            Page<Announcement> announcementsPage = new PageImpl<>(List.of(announcement), pageable, 1);
            Mockito.when(announcementRepository.findAllByAuthorAndStatus(Mockito.any(User.class),
                            Mockito.eq(AnnouncementStatus.SUSPENDED), Mockito.any(Pageable.class)))
                    .thenReturn(announcementsPage);

            Page<AnnouncementDTO> result = announcementService.findAllSuspended(pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.getTotalElements());
            Assertions.assertEquals("Suspended Title", result.getContent().getFirst().title());
            Mockito.verify(announcementRepository, Mockito.times(1)).findAllByAuthorAndStatus(Mockito.any(User.class),
                    Mockito.eq(AnnouncementStatus.SUSPENDED), Mockito.any(Pageable.class));
        }

        @Test
        @DisplayName("Não deve retornar anúncios se nenhum anúncio suspenso for encontrado")
        void shouldReturnEmptyPageIfNoSuspendedAnnouncements() {
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            Pageable pageable = PageRequest.of(0, 5);

            Page<Announcement> emptyPage = Page.empty();
            Mockito.when(announcementRepository.findAllByAuthorAndStatus(Mockito.any(User.class),
                            Mockito.eq(AnnouncementStatus.SUSPENDED), Mockito.any(Pageable.class)))
                    .thenReturn(emptyPage);

            Page<AnnouncementDTO> result = announcementService.findAllSuspended(pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(0, result.getTotalElements());
            Mockito.verify(announcementRepository, Mockito.times(1)).findAllByAuthorAndStatus(Mockito.any(User.class),
                    Mockito.eq(AnnouncementStatus.SUSPENDED), Mockito.any(Pageable.class));
        }

    }

    @Nested
    class FindAllOpen{

        @Test
        @DisplayName("Deve retornar anúncios abertos com sucesso")
        void shouldReturnOpenAnnouncementsSuccessfully() {
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            UUID announcementId = UUID.randomUUID();
            Announcement announcement = new Announcement();
            announcement.setId(announcementId);
            announcement.setTitle("Open Title");
            announcement.setStatus(AnnouncementStatus.VISIBLE);
            announcement.setAuthor(authenticatedUser);

            Pageable pageable = PageRequest.of(0, 5);

            Page<Announcement> announcementsPage = new PageImpl<>(List.of(announcement), pageable, 1);
            Mockito.when(announcementRepository.findAllByAuthorAndStatus(Mockito.any(User.class),
                            Mockito.eq(AnnouncementStatus.VISIBLE), Mockito.any(Pageable.class)))
                    .thenReturn(announcementsPage);

            Page<AnnouncementDTO> result = announcementService.findAllOpen(pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.getTotalElements());
            Assertions.assertEquals("Open Title", result.getContent().getFirst().title());
            Mockito.verify(announcementRepository, Mockito.times(1)).findAllByAuthorAndStatus(Mockito.any(User.class),
                    Mockito.eq(AnnouncementStatus.VISIBLE), Mockito.any(Pageable.class));
        }

        @Test
        @DisplayName("Não deve retornar anúncios se nenhum anúncio aberto for encontrado")
        void shouldReturnEmptyPageIfNoOpenAnnouncements() {
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            Pageable pageable = PageRequest.of(0, 5);

            Page<Announcement> emptyPage = Page.empty();
            Mockito.when(announcementRepository.findAllByAuthorAndStatus(Mockito.any(User.class),
                            Mockito.eq(AnnouncementStatus.VISIBLE), Mockito.any(Pageable.class)))
                    .thenReturn(emptyPage);

            Page<AnnouncementDTO> result = announcementService.findAllOpen(pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(0, result.getTotalElements());
            Mockito.verify(announcementRepository, Mockito.times(1)).findAllByAuthorAndStatus(Mockito.any(User.class),
                    Mockito.eq(AnnouncementStatus.VISIBLE), Mockito.any(Pageable.class));
        }

    }

    @Nested
    class EditById{

        @Test
        @DisplayName("Deve editar o anúncio com sucesso")
        void shouldEditAnnouncementSuccessfully() {
            UUID announcementId = UUID.randomUUID();
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            SaveAnnouncementDTO editAnnouncementDTO = new SaveAnnouncementDTO(
                    "New Title", "Updated Content", UUID.randomUUID(), Set.of(UUID.randomUUID()), "new_image.jpg", 150.0f
            );

            Announcement existingAnnouncement = new Announcement();
            existingAnnouncement.setId(announcementId);
            existingAnnouncement.setAuthor(authenticatedUser);
            existingAnnouncement.setStatus(AnnouncementStatus.VISIBLE);

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(existingAnnouncement));
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);
            Mockito.when(cityService.getById(Mockito.any(UUID.class))).thenReturn(new City());
            Mockito.when(categoryService.getAllById(Mockito.anySet())).thenReturn(new HashSet<>());
            Mockito.when(announcementRepository.save(Mockito.any(Announcement.class))).thenReturn(existingAnnouncement);

            AnnouncementDTO result = announcementService.editById(editAnnouncementDTO, announcementId);

            Assertions.assertNotNull(result);
            Assertions.assertEquals("New Title", result.title());
            Assertions.assertEquals("Updated Content", result.content());
            Assertions.assertEquals(150.0f, result.price());
            Mockito.verify(announcementRepository, Mockito.times(1)).save(Mockito.any(Announcement.class));
        }

        @Test
        @DisplayName("Não deve editar o anúncio se o usuário não for o autor")
        void shouldThrowWithoutAuthorizationExceptionIfUserIsNotAuthor() {
            UUID announcementId = UUID.randomUUID();
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            User otherUser = new User("otheruser@example.com", "Other User", UserType.EMPLOYEE, UserRole.USER);

            SaveAnnouncementDTO editAnnouncementDTO = new SaveAnnouncementDTO(
                    "New Title", "Updated Content", UUID.randomUUID(), Set.of(UUID.randomUUID()), "new_image.jpg", 150.0f
            );

            Announcement existingAnnouncement = new Announcement();
            existingAnnouncement.setId(announcementId);
            existingAnnouncement.setAuthor(otherUser);
            existingAnnouncement.setStatus(AnnouncementStatus.VISIBLE);

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(existingAnnouncement));
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            Assertions.assertThrows(WithoutAuthorizationException.class, () ->
                announcementService.editById(editAnnouncementDTO, announcementId));
        }

        @Test
        @DisplayName("Não deve editar o anúncio se o status for 'DELETED'")
        void shouldThrowAnnouncementIsDeletedExceptionIfAnnouncementIsDeleted() {
            UUID announcementId = UUID.randomUUID();
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            SaveAnnouncementDTO editAnnouncementDTO = new SaveAnnouncementDTO(
                    "New Title", "Updated Content", UUID.randomUUID(), Set.of(UUID.randomUUID()), "new_image.jpg", 150.0f
            );

            Announcement existingAnnouncement = new Announcement();
            existingAnnouncement.setId(announcementId);
            existingAnnouncement.setAuthor(authenticatedUser);
            existingAnnouncement.setStatus(AnnouncementStatus.DELETED);

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(existingAnnouncement));
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            Assertions.assertThrows(AnnouncementIsDeletedException.class, () ->
                announcementService.editById(editAnnouncementDTO, announcementId)
            );
        }

        @Test
        @DisplayName("Não deve editar o anúncio se o status for 'CLOSED'")
        void shouldThrowAnnouncementIsClosedExceptionIfAnnouncementIsClosed() {
            UUID announcementId = UUID.randomUUID();
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            SaveAnnouncementDTO editAnnouncementDTO = new SaveAnnouncementDTO(
                    "New Title", "Updated Content", UUID.randomUUID(), Set.of(UUID.randomUUID()), "new_image.jpg", 150.0f
            );

            Announcement existingAnnouncement = new Announcement();
            existingAnnouncement.setId(announcementId);
            existingAnnouncement.setAuthor(authenticatedUser);
            existingAnnouncement.setStatus(AnnouncementStatus.CLOSED);

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(existingAnnouncement));
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            Assertions.assertThrows(AnnouncementIsClosedException.class, () ->
                announcementService.editById(editAnnouncementDTO, announcementId)
            );
        }

        @Test
        @DisplayName("Deve editar o anúncio com sucesso, sem imagem de arquivo")
        void shouldEditAnnouncementSuccessfullyWithoutImage() {
            UUID announcementId = UUID.randomUUID();
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            SaveAnnouncementDTO editAnnouncementDTO = new SaveAnnouncementDTO(
                    "Updated Title", "Updated Content", UUID.randomUUID(), Set.of(UUID.randomUUID()), null, 120.0f
            );

            Announcement existingAnnouncement = new Announcement();
            existingAnnouncement.setId(announcementId);
            existingAnnouncement.setAuthor(authenticatedUser);
            existingAnnouncement.setStatus(AnnouncementStatus.VISIBLE);

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(existingAnnouncement));
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);
            Mockito.when(cityService.getById(Mockito.any(UUID.class))).thenReturn(new City());
            Mockito.when(categoryService.getAllById(Mockito.anySet())).thenReturn(new HashSet<>());
            Mockito.when(announcementRepository.save(Mockito.any(Announcement.class))).thenReturn(existingAnnouncement);

            AnnouncementDTO result = announcementService.editById(editAnnouncementDTO, announcementId);

            Assertions.assertNotNull(result);
            Assertions.assertEquals("Updated Title", result.title());
            Assertions.assertEquals("Updated Content", result.content());
            Assertions.assertEquals(120.0f, result.price());
            Assertions.assertNull(result.imageArchive());
            Mockito.verify(announcementRepository, Mockito.times(1)).save(Mockito.any(Announcement.class));
        }

        @Test
        @DisplayName("Deve editar o anúncio com sucesso, com imagem de arquivo")
        void shouldEditAnnouncementSuccessfullyWithImage() {
            UUID announcementId = UUID.randomUUID();
            User authenticatedUser = new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER);
            SaveAnnouncementDTO editAnnouncementDTO = new SaveAnnouncementDTO(
                    "Updated Title", "Updated Content", UUID.randomUUID(), Set.of(UUID.randomUUID()), "new_image.jpg", 150.0f
            );

            Announcement existingAnnouncement = new Announcement();
            existingAnnouncement.setId(announcementId);
            existingAnnouncement.setAuthor(authenticatedUser);
            existingAnnouncement.setStatus(AnnouncementStatus.VISIBLE);

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(existingAnnouncement));
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);
            Mockito.when(cityService.getById(Mockito.any(UUID.class))).thenReturn(new City());
            Mockito.when(categoryService.getAllById(Mockito.anySet())).thenReturn(new HashSet<>());
            Mockito.when(announcementRepository.save(Mockito.any(Announcement.class))).thenReturn(existingAnnouncement);

            AnnouncementDTO result = announcementService.editById(editAnnouncementDTO, announcementId);

            Assertions.assertNotNull(result);
            Assertions.assertEquals("Updated Title", result.title());
            Assertions.assertEquals("Updated Content", result.content());
            Assertions.assertEquals(150.0f, result.price());
            Assertions.assertEquals("new_image.jpg", result.imageArchive());
            Mockito.verify(announcementRepository, Mockito.times(1)).save(Mockito.any(Announcement.class));
        }

    }

    @Nested
    class GetById{

        @Test
        @DisplayName("Deve retornar o anúncio quando encontrado pelo ID")
        void shouldReturnAnnouncementWhenFoundById() {
            UUID announcementId = UUID.randomUUID();
            Announcement expectedAnnouncement = new Announcement();
            expectedAnnouncement.setId(announcementId);
            expectedAnnouncement.setTitle("Title");
            expectedAnnouncement.setContent("Content");

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(expectedAnnouncement));

            Announcement result = announcementService.getById(announcementId);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(announcementId, result.getId());
            Assertions.assertEquals("Title", result.getTitle());
            Assertions.assertEquals("Content", result.getContent());
            Mockito.verify(announcementRepository, Mockito.times(1)).findById(announcementId);
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando o anúncio não for encontrado pelo ID")
        void shouldThrowExceptionWhenAnnouncementNotFoundById() {
            UUID announcementId = UUID.randomUUID();

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.empty());

            Assertions.assertThrows(AnnouncementNotFoundException.class, () ->
                announcementService.getById(announcementId));
            Mockito.verify(announcementRepository, Mockito.times(1)).findById(announcementId);
        }

    }

    @Nested
    class Delete{

        @Test
        @DisplayName("Deve excluir o anúncio com sucesso se o usuário for o autor")
        void shouldDeleteAnnouncementSuccessfullyIfUserIsAuthor() {
            UUID announcementId = UUID.randomUUID();
            Announcement announcement = new Announcement();
            announcement.setId(announcementId);
            announcement.setStatus(AnnouncementStatus.VISIBLE);
            announcement.setAuthor(new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER));

            Chat chat = new Chat();
            chat.setId(UUID.randomUUID());
            announcement.setChats(new HashSet<>(List.of(chat)));

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(announcement));
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(announcement.getAuthor());
            Mockito.when(announcementRepository.save(Mockito.any(Announcement.class))).thenReturn(announcement);

            Mockito.when(chatRepository.save(Mockito.any(Chat.class))).thenReturn(chat);

            Mockito.doNothing().when(favoriteRepository).deleteAllByAnnouncement(Mockito.any(Announcement.class));

            announcementService.delete(announcementId);

            Assertions.assertEquals(AnnouncementStatus.DELETED, announcement.getStatus());
            Mockito.verify(announcementRepository, Mockito.times(1)).save(announcement);
            Mockito.verify(favoriteRepository, Mockito.times(1)).deleteAllByAnnouncement(announcement);
            Mockito.verify(chatRepository, Mockito.times(1)).save(Mockito.any(Chat.class));
        }

        @Test
        @DisplayName("Deve lançar exceção se o usuário não for o autor do anúncio")
        void shouldThrowExceptionIfUserIsNotAuthor() {
            UUID announcementId = UUID.randomUUID();
            Announcement announcement = new Announcement();
            announcement.setId(announcementId);
            announcement.setStatus(AnnouncementStatus.VISIBLE);
            announcement.setAuthor(new User("author@example.com", "Author", UserType.EMPLOYEE, UserRole.USER));

            Mockito.when(announcementRepository.findById(announcementId)).thenReturn(Optional.of(announcement));
            Mockito.when(authDetails.getAuthenticatedUser()).thenReturn(new User("user@example.com", "User", UserType.EMPLOYEE, UserRole.USER));

            Assertions.assertThrows(WithoutAuthorizationException.class, () ->
                announcementService.delete(announcementId)
            );
            Mockito.verify(announcementRepository, Mockito.times(0)).save(announcement);
        }

    }

    @Nested
    class GetByAuthor{

        @Test
        @DisplayName("Deve retornar anúncios de um autor com sucesso")
        void shouldReturnAnnouncementsByAuthorSuccessfully() {
            String email = "user@example.com";
            User user = new User(email, "Author", UserType.EMPLOYEE, UserRole.USER);
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("date")));

            Announcement announcement = new Announcement();
            announcement.setId(UUID.randomUUID());
            announcement.setTitle("Anúncio de Teste");
            announcement.setAuthor(user);
            announcement.setStatus(AnnouncementStatus.VISIBLE);

            Page<Announcement> page = new PageImpl<>(List.of(announcement), pageable, 1);

            Mockito.when(userService.getUserByEmail(email)).thenReturn(user);
            Mockito.when(announcementRepository.findAllByAuthorAndStatus(Mockito.any(User.class),
                            Mockito.eq(AnnouncementStatus.VISIBLE), Mockito.any(Pageable.class)))
                    .thenReturn(page);

            Page<AnnouncementDTO> result = announcementService.getByAuthor(email, pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.getTotalElements());
            Assertions.assertEquals("Anúncio de Teste", result.getContent().getFirst().title());
            Mockito.verify(announcementRepository, Mockito.times(1))
                    .findAllByAuthorAndStatus(Mockito.any(User.class), Mockito.eq(AnnouncementStatus.VISIBLE), Mockito.any(Pageable.class));
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando o autor não for encontrado")
        void shouldThrowExceptionWhenAuthorNotFound() {
            String email = "user@example.com";
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("date")));

            Mockito.when(userService.getUserByEmail(email)).thenThrow(new UserNotFoundException());

            Assertions.assertThrows(UserNotFoundException.class, () -> announcementService.getByAuthor(email, pageable));
            Mockito.verify(userService, Mockito.times(1)).getUserByEmail(email);
        }


    }

}