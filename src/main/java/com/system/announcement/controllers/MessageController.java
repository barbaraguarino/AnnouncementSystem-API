package com.system.announcement.controllers;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.chat.ReceiveMessageDTO;
import com.system.announcement.dtos.chat.SendMessageDTO;
import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import com.system.announcement.models.User;
import com.system.announcement.services.ChatService;
import com.system.announcement.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat/message")
public class MessageController {

    private final MessageService messageService;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final AuthDetails authDetails;

    public MessageController(MessageService messageService, ChatService chatService, SimpMessagingTemplate messagingTemplate, AuthDetails authDetails) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
        this.authDetails = authDetails;
    }

    @MessageMapping("/send-message")
    public void sendMessage(@RequestBody ReceiveMessageDTO receiveMessageDTO, Principal principal) {
        Chat chat = chatService.findById(receiveMessageDTO.chat());
        var sender = authDetails.getAuthenticatedUser();
        System.out.println("User: " + sender.getUsername());
        System.out.println("Usando o Principal: " + principal.getName());

        Message message = new Message(chat, sender, receiveMessageDTO.message());
        message = messageService.saveMessage(message);

        var sendMessageDTO = new SendMessageDTO(message);
        messagingTemplate.convertAndSendToUser(chat.getUser().getEmail(), "/queue/messages", sendMessageDTO);
        messagingTemplate.convertAndSendToUser(chat.getAdvertiser().getEmail(), "/queue/messages", sendMessageDTO);

        chat.setDateLastMessage(new Timestamp(System.currentTimeMillis()));
        chatService.save(chat);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Object> getMessages(@PathVariable UUID chatId) {
        Chat chat = chatService.findById(chatId);
        var messages = messageService.getMessagesByChat(chat).stream()
                .map(SendMessageDTO::new)
                .collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}