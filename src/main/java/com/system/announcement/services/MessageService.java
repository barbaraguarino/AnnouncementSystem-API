package com.system.announcement.services;

import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import com.system.announcement.repositories.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public Set<Message> getMessagesByChat(Chat chat) {
        List<Message> messages = messageRepository.findByChatOrderByDateAsc(chat);
        return new HashSet<>(messages);
    }
}
