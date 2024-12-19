package com.system.announcement.controllers;

import com.system.announcement.models.User;
import com.system.announcement.services.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    public final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<Object> getChats(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(chatService.getChats(user));
    }
}
