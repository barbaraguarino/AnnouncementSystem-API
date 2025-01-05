package com.system.announcement.controllers;

import com.system.announcement.dtos.chat.ReceiveMessageDTO;
import com.system.announcement.dtos.chat.SendMessageDTO;
import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import com.system.announcement.services.ChatService;
import com.system.announcement.services.MessageService;
import com.system.announcement.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat/message")
public class MessageController {

    private final MessageService messageService;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    public MessageController(MessageService messageService, ChatService chatService, SimpMessagingTemplate messagingTemplate, UserService userService) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
    }

    @MessageMapping("/send-message")
    public void sendMessage(@RequestBody ReceiveMessageDTO receiveMessageDTO) {
        Chat chat = chatService.findById(receiveMessageDTO.chat());
        var sender = userService.getUserByEmail(receiveMessageDTO.email());

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
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessagesByChat(chatId));
    }
}