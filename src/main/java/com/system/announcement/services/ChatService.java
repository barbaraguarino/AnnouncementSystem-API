package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.ChatStatus;
import com.system.announcement.exceptions.AnnouncementNotFoundException;
import com.system.announcement.exceptions.ChatNotFoundException;
import com.system.announcement.exceptions.NoAuthorizationException;
import com.system.announcement.models.Announcement;
import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import com.system.announcement.models.User;
import com.system.announcement.repositories.AnnouncementRepository;
import com.system.announcement.repositories.ChatRepository;
import com.system.announcement.repositories.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final AuthDetails authDetails;
    private final AnnouncementRepository announcementRepository;

    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, AuthDetails authDetails, AnnouncementRepository announcementRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.authDetails = authDetails;
        this.announcementRepository = announcementRepository;
    }

    public boolean canStartChat(User user, Announcement announcement) {
        // Verifica se o usuário é o autor do anúncio
        if (announcement.getAuthor().getEmail().equals(user.getEmail())) {
            return false;
        }

        // Verifica se o chat já existe
        return !announcement.getChats().stream()
                .anyMatch(chat -> chat.getUser().getEmail().equals(user.getEmail()));
    }

    public boolean canCloseChat(User user, Chat chat) {
        // Apenas o autor do anúncio pode fechar o chat
        return chat.getAnnouncement().getAuthor().getEmail().equals(user.getEmail());
    }
}

