package com.system.announcement.controllers;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.chat.ReceiveMessageDTO;
import com.system.announcement.dtos.chat.SendMessageDTO;
import com.system.announcement.models.Message;
import com.system.announcement.repositories.ChatRepository;
import com.system.announcement.repositories.MessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final MessageRepository messageRepository;
    private final AuthDetails authDetails;
    private final ChatRepository chatRepository;

    public ChatController(MessageRepository messageRepository, AuthDetails authDetails, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.authDetails = authDetails;
        this.chatRepository = chatRepository;
    }

    @MessageMapping("/chat/send") // Endpoint para receber mensagens
    @SendTo("/topic/chat") // Canal para broadcast das mensagens
    public SendMessageDTO sendMessage(ReceiveMessageDTO messageDTO) {
        Message message = new Message();
        message.setChat(chatRepository.findById(messageDTO.chat()).orElseThrow());
        message.setSender(authDetails.getAuthenticatedUser());
        message.setContent(messageDTO.message());
        message = messageRepository.save(message);

        return new SendMessageDTO(message);
    }
}

