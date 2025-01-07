package com.system.announcement.controllers;

import com.system.announcement.dtos.chat.ReceiveMessageDTO;
import com.system.announcement.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        return ResponseEntity.status(HttpStatus.OK)
                .body(messageService.getMessagesByChat(chatId));
    }
}
