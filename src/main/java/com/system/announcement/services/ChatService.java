package com.system.announcement.services;

import com.system.announcement.exceptions.ChatNotFoundException;
import com.system.announcement.models.Chat;
import com.system.announcement.repositories.ChatRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat findById(@NotNull UUID chat) {
        return chatRepository.findById(chat).orElseThrow(ChatNotFoundException::new);
    }
}

