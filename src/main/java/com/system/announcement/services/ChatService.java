package com.system.announcement.services;

import com.system.announcement.dtos.chat.ChatDTO;
import com.system.announcement.exceptions.ChatNotFoundException;
import com.system.announcement.infra.specifications.ChatSpecification;
import com.system.announcement.models.Chat;
import com.system.announcement.models.User;
import com.system.announcement.repositories.ChatRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<ChatDTO> getChats(User user, Pageable pageable) {
        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("dateLastMessage")));
        Page<Chat> chats = chatRepository.findAll(new ChatSpecification(user), pageableWithSorting);
        return chats.map((chat) -> new ChatDTO(chat, user));
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }
}
