package com.system.announcement.controllers;

import com.system.announcement.services.ChatService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {

    public final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<Object> getChats(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(chatService.getChats(pageable));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> createChat(@PathVariable @Valid UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createChat(id));
    }
}
