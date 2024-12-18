package com.system.announcement.controllers;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.chat.ReceiveMessageDTO;
import com.system.announcement.dtos.chat.SendMessageDTO;
import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import com.system.announcement.models.User;
import com.system.announcement.services.ChatService;
import com.system.announcement.services.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService; // Serviço para persistir as mensagens
    private final ChatService chatService;
    private final AuthDetails authDetails;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService, ChatService chatService, AuthDetails authDetails) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
        this.chatService = chatService;
        this.authDetails = authDetails;
    }

    @MessageMapping("/send")
    @Transactional
    public void sendMessage(ReceiveMessageDTO messageDTO) {
        // Buscar o chat e validar se o usuário está autorizado
        Chat chat = chatService.findById(messageDTO.chat());
        if (chat != null && isUserInChat(chat)) {
            // Salvar a mensagem no banco
            User sender = authDetails.getAuthenticatedUser();
            Message message = new Message(chat, sender, messageDTO.message());
            message = messageService.save(message);

            // Enviar a mensagem para todos os membros do chat
            SendMessageDTO sendMessageDTO = new SendMessageDTO(message);
            messagingTemplate.convertAndSend("/topic/chat/" + sendMessageDTO);
        }
    }

    private boolean isUserInChat(Chat chat) {
        User authenticatedUser = authDetails.getAuthenticatedUser();
        return chat.getUser().equals(authenticatedUser) || chat.getAdvertiser().equals(authenticatedUser);
    }
}

