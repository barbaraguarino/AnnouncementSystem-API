package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.chat.ChatDTO;
import com.system.announcement.exceptions.ChatNotFoundException;
import com.system.announcement.exceptions.NoAuthorizationException;
import com.system.announcement.infra.specifications.ChatSpecification;
import com.system.announcement.models.Chat;
import com.system.announcement.repositories.ChatRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final AuthDetails authDetails;
    private final AnnouncementService announcementService;

    public ChatService(ChatRepository chatRepository, AuthDetails authDetails, AnnouncementService announcementService) {
        this.chatRepository = chatRepository;
        this.authDetails = authDetails;
        this.announcementService = announcementService;
    }

    public Chat findById(@NotBlank UUID chat) {
        return chatRepository.findById(chat).orElseThrow(ChatNotFoundException::new);
    }

    public Page<ChatDTO> getChats(Pageable pageable) {
        var user = authDetails.getAuthenticatedUser();
        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("dateLastMessage")));
        Page<Chat> chats = chatRepository.findAll(new ChatSpecification(user), pageableWithSorting);
        return chats.map((chat) -> new ChatDTO(chat, user));
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    public ChatDTO createChat(@Valid UUID idAnnouncement) {
        var announcement = announcementService.getAnnouncementById(idAnnouncement);
        var user = authDetails.getAuthenticatedUser();
        if(announcement.getAuthor().getEmail().equals(user.getEmail())) throw new NoAuthorizationException();
        var chatOptional = chatRepository.findChatByAnnouncementAndUser(announcement, user);
        if(chatOptional.isPresent()) return new ChatDTO(chatOptional.get(), user);
        var chat = new Chat(user, announcement);
        chat = chatRepository.save(chat);
        return new ChatDTO(chat, user);
    }

    public ChatDTO closeChat(@Valid UUID idChat) {
        var chat = chatRepository.findById(idChat).orElseThrow(ChatNotFoundException::new);
        var user = authDetails.getAuthenticatedUser();
        if(chat.getAdvertiser().getEmail().equals(user.getEmail()) || chat.getUser().getEmail().equals(user.getEmail())) {
            chat.close();
            chatRepository.save(chat);
            return new ChatDTO(chat, user);
        }
        throw new NoAuthorizationException();
    }
}
