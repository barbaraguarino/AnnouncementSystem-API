package com.system.announcement.controllers;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.chat.ReceiveMessageDTO;
import com.system.announcement.dtos.chat.SendMessageDTO;
import com.system.announcement.exceptions.AnnouncementNotFoundException;
import com.system.announcement.exceptions.ChatNotFoundException;
import com.system.announcement.exceptions.NoAuthorizationException;
import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import com.system.announcement.repositories.AnnouncementRepository;
import com.system.announcement.repositories.ChatRepository;
import com.system.announcement.repositories.MessageRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final MessageRepository messageRepository;
    private final AuthDetails authDetails;
    private final ChatRepository chatRepository;
    private final AnnouncementRepository announcementRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(MessageRepository messageRepository, AuthDetails authDetails, ChatRepository chatRepository, AnnouncementRepository announcementRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.authDetails = authDetails;
        this.chatRepository = chatRepository;
        this.announcementRepository = announcementRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Object> createChat(@NotNull @PathVariable("id") UUID id) {
        var annoncementOptional = announcementRepository.findById(id);
        if(annoncementOptional.isEmpty()) throw new AnnouncementNotFoundException();
        var announcement  = annoncementOptional.get();
        var user = authDetails.getAuthenticatedUser();
        if(announcement.getAuthor().getEmail().equals(user.getEmail())) throw new NoAuthorizationException();
        var charOptional = chatRepository.findChatByUserAndAnnouncement(user, announcement);
        if(charOptional.isPresent())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(charOptional.get().getId());
        var chat = new Chat(user, announcement);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRepository.save(chat));

    }

    @MessageMapping("/chat")
    public void sendMessage(ReceiveMessageDTO messageDTO) {
        var chat = chatRepository.findById(messageDTO.chat()).orElseThrow(ChatNotFoundException::new);
        var user = authDetails.getAuthenticatedUser();

        if(chat.getUser().getEmail().equals(user.getEmail()) || chat.getAnnouncement().getAuthor().getEmail().equals(user.getEmail())){
            Message message = new Message();
            message.setChat(chat);
            message.setSender(user);
            message.setContent(messageDTO.message());
            message = messageRepository.save(message);

            sendToChatParticipants(message);
        }else throw new NoAuthorizationException();
    }

    private void sendToChatParticipants(Message message) {
        messagingTemplate.convertAndSend("/topic/chat/" + message.getChat().getId(), new SendMessageDTO(message));
    }


}

