package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.dtos.announcement.AnnouncementDTO;
import com.system.announcement.models.Announcement;
import com.system.announcement.models.Favorite;
import com.system.announcement.models.User;
import com.system.announcement.repositories.FavoriteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock
    private AnnouncementService announcementService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private AuthDetails authDetails;

    @InjectMocks
    private FavoriteService favoriteService;

    @Nested
    class SaveFavorite {

        @Test
        @DisplayName("Deve salvar um anúncio como favorito com sucesso")
        void shouldSaveFavoriteSuccessfully() {
            UUID announcementId = UUID.randomUUID();
            Announcement announcement = mock(Announcement.class);
            User user = mock(User.class);

            when(announcementService.getById(announcementId)).thenReturn(announcement);
            when(authDetails.getAuthenticatedUser()).thenReturn(user);

            favoriteService.saveFavorite(announcementId);

            verify(favoriteRepository).save(any(Favorite.class));
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar favorito para um anúncio inexistente")
        void shouldThrowExceptionWhenSavingNonExistentFavorite() {
            UUID announcementId = UUID.randomUUID();

            when(announcementService.getById(announcementId)).thenThrow(EntityNotFoundException.class);

            Assertions.assertThrows(EntityNotFoundException.class, () ->
                    favoriteService.saveFavorite(announcementId)
            );

            verify(favoriteRepository, never()).save(any(Favorite.class));
        }

    }

    @Nested
    class IsFavorite {

        @Test
        @DisplayName("Deve retornar true para anúncio favorito")
        void shouldReturnTrueForFavoriteAnnouncement() {
            UUID announcementId = UUID.randomUUID();
            Announcement announcement = mock(Announcement.class);
            User user = mock(User.class);

            when(announcementService.getById(announcementId)).thenReturn(announcement);
            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(favoriteRepository.existsFavoriteByAnnouncementAndUser(announcement, user)).thenReturn(true);

            boolean result = favoriteService.isFavorite(announcementId);

            Assertions.assertTrue(result);
            verify(favoriteRepository).existsFavoriteByAnnouncementAndUser(announcement, user);
        }

        @Test
        @DisplayName("Deve retornar false para anúncio não favorito")
        void shouldReturnFalseForNonFavoriteAnnouncement() {
            UUID announcementId = UUID.randomUUID();
            Announcement announcement = mock(Announcement.class);
            User user = mock(User.class);

            when(announcementService.getById(announcementId)).thenReturn(announcement);
            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(favoriteRepository.existsFavoriteByAnnouncementAndUser(announcement, user)).thenReturn(false);

            boolean result = favoriteService.isFavorite(announcementId);

            Assertions.assertFalse(result);
            verify(favoriteRepository).existsFavoriteByAnnouncementAndUser(announcement, user);
        }

    }

    @Nested
    class GetMyAllFavorite {

        @Test
        @DisplayName("Deve retornar todos os favoritos do usuário")
        void shouldReturnAllFavoritesForUser() {
            User user = mock(User.class);
            Pageable pageable = mock(Pageable.class);

            Favorite favorite = mock(Favorite.class);
            Announcement announcement = mock(Announcement.class);
            User author = mock(User.class);

            when(author.getEmail()).thenReturn("author@example.com");
            when(author.getName()).thenReturn("Author Name");

            when(announcement.getAuthor()).thenReturn(author);
            when(announcement.getId()).thenReturn(UUID.randomUUID());
            when(announcement.getTitle()).thenReturn("Título do Anúncio");

            when(announcement.getStatus()).thenReturn(AnnouncementStatus.VISIBLE);

            when(favorite.getAnnouncement()).thenReturn(announcement);

            Page<Favorite> favorites = new PageImpl<>(List.of(favorite), pageable, 1);

            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(favoriteRepository.getAllByUser(user, pageable)).thenReturn(favorites);

            Page<AnnouncementDTO> result = favoriteService.getMyAllFavorite(pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.getTotalElements());
            verify(favoriteRepository).getAllByUser(user, pageable);
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia se o usuário não tiver favoritos")
        void shouldReturnEmptyListForUserWithoutFavorites() {
            User user = mock(User.class);
            Pageable pageable = mock(Pageable.class);
            Page<Favorite> emptyFavorites = Page.empty();

            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(favoriteRepository.getAllByUser(user, pageable)).thenReturn(emptyFavorites);

            Page<AnnouncementDTO> result = favoriteService.getMyAllFavorite(pageable);

            Assertions.assertTrue(result.isEmpty());
            verify(favoriteRepository).getAllByUser(user, pageable);
        }

    }

    @Nested
    class RemoveFavorite{

        @Test
        @DisplayName("Deve remover favorito com sucesso")
        void shouldRemoveFavoriteSuccessfully() {
            UUID announcementId = UUID.randomUUID();
            Announcement announcement = mock(Announcement.class);
            User user = mock(User.class);

            when(announcementService.getById(announcementId)).thenReturn(announcement);
            when(authDetails.getAuthenticatedUser()).thenReturn(user);

            favoriteService.removeFavorite(announcementId);

            verify(favoriteRepository).deleteByAnnouncementAndUser(announcement, user);
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar remover favorito inexistente")
        void shouldThrowExceptionWhenRemovingNonExistentFavorite() {
            UUID announcementId = UUID.randomUUID();

            when(announcementService.getById(announcementId)).thenThrow(EntityNotFoundException.class);

            Assertions.assertThrows(EntityNotFoundException.class, () ->
                    favoriteService.removeFavorite(announcementId)
            );

            verify(favoriteRepository, never()).deleteByAnnouncementAndUser(any(), any());
        }

    }

}