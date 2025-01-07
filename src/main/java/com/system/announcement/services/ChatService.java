package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.auxiliary.enums.ChatStatus;
import com.system.announcement.dtos.chat.ChatDTO;
import com.system.announcement.exceptions.*;
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

    public ChatService(ChatRepository chatRepository,
                       AuthDetails authDetails,
                       AnnouncementService announcementService) {
        this.chatRepository = chatRepository;
        this.authDetails = authDetails;
        this.announcementService = announcementService;
    }

    public Chat findById(@NotBlank UUID chat) {
        return chatRepository.findById(chat)
                .orElseThrow(ChatNotFoundException::new);
    }

    public Page<ChatDTO> getChats(Pageable pageable) {
        var user = authDetails.getAuthenticatedUser();

        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), Sort.by(Sort.Order.desc("dateLastMessage")));

        Page<Chat> chats = chatRepository
                .findAll(new ChatSpecification(user), pageableWithSorting);

        return chats.map((chat) -> new ChatDTO(chat, user));
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    public ChatDTO createChat(@Valid UUID idAnnouncement) {
        var announcement = announcementService.getById(idAnnouncement);

        if(announcement.getStatus().equals(AnnouncementStatus.DELETED))
            throw new AnnouncementIsDeletedException();

        if(announcement.getStatus().equals(AnnouncementStatus.CLOSED))
            throw new AnnouncementIsClosedException();

        var user = authDetails.getAuthenticatedUser();

        if(announcement.getAuthor().getEmail().equals(user.getEmail()))
            throw new WithoutAuthorizationException();

        var chatOptional = chatRepository.findChatByAnnouncementAndUser(announcement, user);

        if(chatOptional.isPresent()) {
            var chat = chatOptional.get();

            if(chat.getStatus() == ChatStatus.OPEN)
                return new ChatDTO(chat, user);

            chat.setStatus(ChatStatus.OPEN);
            chat = chatRepository.save(chat);

            return new ChatDTO(chat, user);
        }

        var chat = new Chat(user, announcement);
        chat = chatRepository.save(chat);

        return new ChatDTO(chat, user);
    }

    public ChatDTO closeChat(@Valid UUID idChat) {

        var chat = this.findById(idChat);
        var user = authDetails.getAuthenticatedUser();

        if(chat.getAdvertiser().getEmail().equals(user.getEmail())
                || chat.getUser().getEmail().equals(user.getEmail())) {

            chat.close();
            chatRepository.save(chat);

            return new ChatDTO(chat, user);
        }

        throw new WithoutAuthorizationException();
    }
}
