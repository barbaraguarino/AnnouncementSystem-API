package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.auxiliary.enums.UserRole;
import com.system.announcement.auxiliary.enums.UserType;
import com.system.announcement.dtos.chat.ChatDTO;
import com.system.announcement.exceptions.AnnouncementIsDeletedException;
import com.system.announcement.exceptions.ChatNotFoundException;
import com.system.announcement.infra.specifications.ChatSpecification;
import com.system.announcement.models.Announcement;
import com.system.announcement.models.Chat;
import com.system.announcement.models.User;
import com.system.announcement.repositories.ChatRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private AuthDetails authDetails;

    @Mock
    private AnnouncementService announcementService;

    @InjectMocks
    private ChatService chatService;

    @Nested
    class FindById {

        @Test
        @DisplayName("Deve retornar chat existente pelo ID")
        void shouldReturnChatById() {
            UUID chatId = UUID.randomUUID();
            Chat chat = mock(Chat.class);

            when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

            Chat result = chatService.findById(chatId);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(chat, result);
            verify(chatRepository).findById(chatId);
        }

        @Test
        @DisplayName("Deve lançar exceção quando chat não for encontrado")
        void shouldThrowExceptionWhenChatNotFound() {
            UUID chatId = UUID.randomUUID();

            when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

            Assertions.assertThrows(ChatNotFoundException.class, () -> chatService.findById(chatId));

            verify(chatRepository).findById(chatId);
        }
    }

    @Nested
    class GetChats {

        @Test
        @DisplayName("Deve retornar página de chats para o usuário autenticado")
        void shouldReturnChatsForAuthenticatedUser() {
            User authenticatedUser = new User("user@example.com", "User", UserType.TEACHER, UserRole.USER);

            Announcement announcement = mock(Announcement.class);
            when(announcement.getId()).thenReturn(UUID.randomUUID());
            when(announcement.getTitle()).thenReturn("Título do Anúncio");
            when(announcement.getStatus()).thenReturn(AnnouncementStatus.VISIBLE);

            Chat chat = mock(Chat.class);
            when(chat.getUser()).thenReturn(authenticatedUser);
            when(chat.getAdvertiser()).thenReturn(new User("advertiser@example.com", "Advertiser", UserType.EMPLOYEE, UserRole.USER));
            when(chat.getAnnouncement()).thenReturn(announcement);

            Pageable pageable = PageRequest.of(0, 10);
            Page<Chat> chats = new PageImpl<>(List.of(chat));

            when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);
            when(chatRepository.findAll(any(ChatSpecification.class), any(Pageable.class))).thenReturn(chats);

            Page<ChatDTO> result = chatService.getChats(pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.getTotalElements());
            Assertions.assertEquals("advertiser@example.com", result.getContent().getFirst().participant().email());
            Assertions.assertEquals("Título do Anúncio", result.getContent().getFirst().announcement().title());

            verify(authDetails).getAuthenticatedUser();
            verify(chatRepository).findAll(any(ChatSpecification.class), any(Pageable.class));
        }

        @Test
        @DisplayName("Deve retornar página vazia quando não houver chats")
        void shouldReturnEmptyPageWhenNoChatsFound() {
            User user = mock(User.class);
            Pageable pageable = PageRequest.of(0, 10);
            Page<Chat> chats = Page.empty();
            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(chatRepository.findAll(any(ChatSpecification.class), any(Pageable.class))).thenReturn(chats);

            Page<ChatDTO> result = chatService.getChats(pageable);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(0, result.getTotalElements());
            verify(authDetails).getAuthenticatedUser();
            verify(chatRepository).findAll(any(ChatSpecification.class), any(Pageable.class));
        }
    }

    @Nested
    class Save {

        @Test
        @DisplayName("Deve salvar o chat")
        void shouldSaveChat() {
            Chat chat = mock(Chat.class);

            chatService.save(chat);

            verify(chatRepository).save(chat);
        }
    }

    @Nested
    class CreateChat {

        @Test
        @DisplayName("Deve criar novo chat para o anúncio")
        void shouldCreateChatForAnnouncement() {
            UUID announcementId = UUID.randomUUID();
            Announcement announcement = mock(Announcement.class);
            User user = mock(User.class);
            User author = mock(User.class);

            lenient().when(announcement.getStatus()).thenReturn(AnnouncementStatus.VISIBLE);
            lenient().when(announcement.getAuthor()).thenReturn(author);
            lenient().when(author.getEmail()).thenReturn("author@example.com");
            lenient().when(user.getEmail()).thenReturn("user@example.com");
            lenient().when(user.getName()).thenReturn("User Name");

            when(announcementService.getById(announcementId)).thenReturn(announcement);
            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(chatRepository.save(any(Chat.class))).thenAnswer(invocation -> {
                Chat savedChat = invocation.getArgument(0);
                savedChat.setId(UUID.randomUUID());
                return savedChat;
            });

            ChatDTO result = chatService.createChat(announcementId);

            Assertions.assertNotNull(result);
            Assertions.assertEquals("author@example.com", result.participant().email());
            verify(announcementService).getById(announcementId);
            verify(authDetails).getAuthenticatedUser();
            verify(chatRepository).save(any(Chat.class));
        }

        @Test
        @DisplayName("Deve lançar exceção se o anúncio estiver deletado")
        void shouldThrowExceptionWhenAnnouncementIsDeleted() {
            UUID announcementId = UUID.randomUUID();
            Announcement announcement = mock(Announcement.class);

            when(announcementService.getById(announcementId)).thenReturn(announcement);
            when(announcement.getStatus()).thenReturn(AnnouncementStatus.DELETED);

            Assertions.assertThrows(AnnouncementIsDeletedException.class, () ->
                    chatService.createChat(announcementId));

            verify(announcementService).getById(announcementId);
        }
    }

    @Nested
    class CloseChat {

        @Test
        @DisplayName("Deve fechar o chat com autorização")
        void shouldCloseChatWithAuthorization() {
            UUID chatId = UUID.randomUUID();
            Chat chat = mock(Chat.class);
            User advertiser = mock(User.class);
            User participant = mock(User.class);
            User authenticatedUser = mock(User.class);
            Announcement announcement = mock(Announcement.class);

            when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));
            when(authDetails.getAuthenticatedUser()).thenReturn(authenticatedUser);

            when(chat.getAdvertiser()).thenReturn(advertiser);
            when(chat.getUser()).thenReturn(participant);
            when(chat.getAnnouncement()).thenReturn(announcement);

            when(advertiser.getEmail()).thenReturn("advertiser@example.com");
            when(participant.getEmail()).thenReturn("participant@example.com");
            when(authenticatedUser.getEmail()).thenReturn("advertiser@example.com");

            when(announcement.getId()).thenReturn(UUID.randomUUID());
            when(announcement.getTitle()).thenReturn("Anúncio Teste");
            when(announcement.getStatus()).thenReturn(AnnouncementStatus.VISIBLE);

            ChatDTO result = chatService.closeChat(chatId);

            Assertions.assertNotNull(result);
            verify(chatRepository).findById(chatId);
            verify(authDetails).getAuthenticatedUser();
            verify(chat).close();
            verify(chatRepository).save(chat);
        }
    }
}
