package com.system.announcement.services;

import com.system.announcement.dtos.chat.SendMessageDTO;
import com.system.announcement.models.Message;
import com.system.announcement.repositories.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatService chatService;

    public MessageService(MessageRepository messageRepository, ChatService chatService) {
        this.messageRepository = messageRepository;
        this.chatService = chatService;
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<SendMessageDTO> getMessagesByChat(UUID idChat) {
        var chat = chatService.findById(idChat);
        List<Message> messages = messageRepository.findByChatOrderByDateAsc(chat);
        return messages.stream()
                .map(SendMessageDTO::new)
                .collect(Collectors.toList());
    }

}
