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

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/send-message")
    public void sendMessage(@RequestBody ReceiveMessageDTO receiveMessageDTO) {
        messageService.sendMessage(receiveMessageDTO);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Object> getMessages(@PathVariable UUID chatId) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessagesByChat(chatId));
    }
}