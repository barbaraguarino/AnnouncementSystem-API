package com.system.announcement.dtos.chat;

import com.system.announcement.dtos.User.UserBasicDTO;
import com.system.announcement.models.Message;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.UUID;

public record SendMessageDTO(
        @NotNull UUID id,
        @NotNull Timestamp date,
        @NotBlank UserBasicDTO sender,
        @NotBlank String status,
        @NotBlank String message
){
    public SendMessageDTO(Message message){
        this(
                message.getId(),
                message.getDate(),
                new UserBasicDTO(message.getSender()),
                message.getStatus().getStatus(),
                message.getContent()
        );
    }
}
