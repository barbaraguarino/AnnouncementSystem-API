package com.system.announcement.controllers;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.chat.ReceiveMessageDTO;
import com.system.announcement.dtos.chat.SendMessageDTO;
import com.system.announcement.exceptions.AnnouncementNotFoundException;
import com.system.announcement.exceptions.ChatNotFoundException;
import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import com.system.announcement.repositories.AnnouncementRepository;
import com.system.announcement.repositories.ChatRepository;
import com.system.announcement.repositories.MessageRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final MessageRepository messageRepository;
    private final AuthDetails authDetails;
    private final ChatRepository chatRepository;
    private final AnnouncementRepository announcementRepository;

    public ChatController(MessageRepository messageRepository, AuthDetails authDetails, ChatRepository chatRepository, AnnouncementRepository announcementRepository) {
        this.messageRepository = messageRepository;
        this.authDetails = authDetails;
        this.chatRepository = chatRepository;
        this.announcementRepository = announcementRepository;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Object> createChat(@NotNull @PathVariable("id") UUID id) {
        var annoncementOptional = announcementRepository.findById(id);
        if(annoncementOptional.isEmpty()) throw new AnnouncementNotFoundException();
        var announcement  = annoncementOptional.get();
        var user = authDetails.getAuthenticatedUser();
        var charOptional = chatRepository.findChatByUserAndAnnouncement(user, announcement);
        if(charOptional.isPresent())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(charOptional.get().getId());
        var chat = new Chat(user, announcement);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRepository.save(chat));

    }

    @MessageMapping("/chat/send") // Endpoint para receber mensagens
    @SendTo("/topic/chat") // Canal para broadcast das mensagens
    public SendMessageDTO sendMessage(ReceiveMessageDTO messageDTO) {
        Message message = new Message();
        message.setChat(chatRepository.findById(messageDTO.chat()).orElseThrow(
                ChatNotFoundException::new
        ));
        message.setSender(authDetails.getAuthenticatedUser());
        message.setContent(messageDTO.message());
        message = messageRepository.save(message);

        return new SendMessageDTO(message);
    }
}

