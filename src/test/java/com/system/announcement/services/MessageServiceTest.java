package com.system.announcement.services;

import com.system.announcement.auxiliary.enums.ChatStatus;
import com.system.announcement.auxiliary.enums.UserRole;
import com.system.announcement.auxiliary.enums.UserType;
import com.system.announcement.dtos.chat.ReceiveMessageDTO;
import com.system.announcement.dtos.chat.SendMessageDTO;
import com.system.announcement.exceptions.ChatNotFoundException;
import com.system.announcement.exceptions.UserNotFoundException;
import com.system.announcement.exceptions.WithoutAuthorizationException;
import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import com.system.announcement.models.User;
import com.system.announcement.repositories.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ChatService chatService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private UserService userService;

    @InjectMocks
    private MessageService messageService;

    @Nested
    class GetMessagesByChat {
        @Test
        @DisplayName("Deve retornar mensagens ordenadas por data para um chat existente")
        void shouldReturnMessagesOrderedByDateForExistingChat() {
            UUID chatId = UUID.randomUUID();
            Chat chat = mock(Chat.class);
            List<Message> messages = List.of(
                    new Message(chat, mock(User.class), "Mensagem 1"),
                    new Message(chat, mock(User.class), "Mensagem 2")
            );

            when(chatService.findById(chatId)).thenReturn(chat);
            when(messageRepository.findByChatOrderByDateAsc(chat)).thenReturn(messages);

            List<SendMessageDTO> result = messageService.getMessagesByChat(chatId);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.size());
            verify(chatService).findById(chatId);
            verify(messageRepository).findByChatOrderByDateAsc(chat);
        }

        @Test
        @DisplayName("Deve lançar exceção para chat inexistente")
        void shouldThrowExceptionForNonExistentChat() {
            UUID chatId = UUID.randomUUID();

            when(chatService.findById(chatId)).thenThrow(ChatNotFoundException.class);

            Assertions.assertThrows(ChatNotFoundException.class, () ->
                    messageService.getMessagesByChat(chatId)
            );

            verify(chatService).findById(chatId);
            verifyNoInteractions(messageRepository);
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não houver mensagens")
        void shouldReturnEmptyListWhenNoMessagesFound() {
            UUID chatId = UUID.randomUUID();
            Chat chat = mock(Chat.class);

            when(chatService.findById(chatId)).thenReturn(chat);
            when(messageRepository.findByChatOrderByDateAsc(chat)).thenReturn(Collections.emptyList());

            List<SendMessageDTO> result = messageService.getMessagesByChat(chatId);

            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isEmpty());
            verify(chatService).findById(chatId);
            verify(messageRepository).findByChatOrderByDateAsc(chat);
        }
    }

    @Nested
    class SendMessage {

        @Test
        @DisplayName("Deve lançar exceção para chat inexistente")
        void shouldThrowExceptionForNonExistentChat() {
            var chatID = UUID.randomUUID();
            ReceiveMessageDTO dto = new ReceiveMessageDTO(chatID, "user@example.com", "Mensagem");

            when(chatService.findById(dto.chat())).thenThrow(ChatNotFoundException.class);

            Assertions.assertThrows(ChatNotFoundException.class, () ->
                    messageService.sendMessage(dto)
            );

            verify(chatService).findById(dto.chat());
            verifyNoInteractions(userService, messageRepository, messagingTemplate);
        }

        @Test
        @DisplayName("Deve enviar mensagem para ambos os usuários do chat")
        void shouldSendMessageToBothChatUsers() {
            User user = new User("advertiser@example.com", "Advertiser", UserType.EMPLOYEE, UserRole.USER);
            User advertiser = new User("user@example.com", "User", UserType.TEACHER, UserRole.USER);
            Chat chat = mock(Chat.class);
            var chatID = UUID.randomUUID();

            when(chat.getUser()).thenReturn(user);
            when(chat.getAdvertiser()).thenReturn(advertiser);
            when(chat.getStatus()).thenReturn(ChatStatus.OPEN);

            ReceiveMessageDTO dto = new ReceiveMessageDTO(chatID, advertiser.getEmail(), "Mensagem");
            Message message = new Message(chat, advertiser, dto.message());

            when(chatService.findById(dto.chat())).thenReturn(chat);
            when(userService.getUserByEmail(dto.email())).thenReturn(advertiser);
            when(messageRepository.save(any(Message.class))).thenReturn(message);

            messageService.sendMessage(dto);

            verify(chatService).findById(dto.chat());
            verify(userService).getUserByEmail(dto.email());
            verify(messageRepository).save(argThat(msg ->
                    msg.getChat().equals(chat) &&
                            msg.getSender().equals(advertiser) &&
                            msg.getContent().equals(dto.message())
            ));
            verify(messagingTemplate).convertAndSendToUser(eq(user.getEmail()), eq("/queue/messages"), any(SendMessageDTO.class));
            verify(messagingTemplate).convertAndSendToUser(eq(advertiser.getEmail()), eq("/queue/messages"), any(SendMessageDTO.class));
            verify(chatService).save(chat);
        }

        @Test
        @DisplayName("Deve lançar exceção para chat com status fechado")
        void shouldThrowExceptionForClosedChat() {
            User advertiser = new User("user@example.com", "User", UserType.TEACHER, UserRole.USER);
            Chat chat = mock(Chat.class);
            var chatID = UUID.randomUUID();

            when(chat.getStatus()).thenReturn(ChatStatus.CLOSED);
            when(chatService.findById(chatID)).thenReturn(chat);

            ReceiveMessageDTO dto = new ReceiveMessageDTO(chatID, advertiser.getEmail(), "Mensagem");

            Assertions.assertThrows(WithoutAuthorizationException.class, () -> messageService.sendMessage(dto));

            verify(chatService).findById(dto.chat());
            verifyNoInteractions(userService, messageRepository, messagingTemplate);
        }

    }

}