package com.system.announcement.services;

import com.system.announcement.dtos.chat.ChatDTO;
import com.system.announcement.exceptions.ChatNotFoundException;
import com.system.announcement.models.Chat;
import com.system.announcement.models.User;
import com.system.announcement.repositories.ChatRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat findById(@NotBlank UUID chat) {
        return chatRepository.findById(chat).orElseThrow(ChatNotFoundException::new);
    }

    public Set<ChatDTO> getChats(User user) {
        return chatRepository.findAllByUserOrAdvertiserOrderByDateLastMessageDesc(user, user).stream()
                .map((chat) -> {
                    return new ChatDTO(chat, user);
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }
}
