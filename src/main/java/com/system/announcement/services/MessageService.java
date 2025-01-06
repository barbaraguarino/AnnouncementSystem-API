package com.system.announcement.services;

import com.system.announcement.dtos.chat.ReceiveMessageDTO;
import com.system.announcement.dtos.chat.SendMessageDTO;
import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import com.system.announcement.repositories.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository,
                          ChatService chatService,
                          SimpMessagingTemplate messagingTemplate,
                          UserService userService) {
        this.messageRepository = messageRepository;
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
    }

    public List<SendMessageDTO> getMessagesByChat(UUID idChat) {
        var chat = chatService.findById(idChat);

        List<Message> messages = messageRepository.findByChatOrderByDateAsc(chat);

        return messages.stream()
                .map(SendMessageDTO::new)
                .collect(Collectors.toList());
    }

    public void sendMessage(ReceiveMessageDTO receiveMessageDTO) {
        Chat chat = chatService.findById(receiveMessageDTO.chat());
        var sender = userService.getUserByEmail(receiveMessageDTO.email());

        Message message = new Message(chat, sender, receiveMessageDTO.message());
        message = messageRepository.save(message);

        var sendMessageDTO = new SendMessageDTO(message);

        messagingTemplate.convertAndSendToUser(chat.getUser().getEmail(),
                "/queue/messages", sendMessageDTO);
        messagingTemplate.convertAndSendToUser(chat.getAdvertiser().getEmail(),
                "/queue/messages", sendMessageDTO);

        chat.setDateLastMessage(new Timestamp(System.currentTimeMillis()));
        chatService.save(chat);
    }
}