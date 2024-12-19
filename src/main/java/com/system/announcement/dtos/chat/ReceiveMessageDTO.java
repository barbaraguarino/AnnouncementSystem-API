package com.system.announcement.dtos.chat;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ReceiveMessageDTO(
        @NotBlank UUID chat,
        @NotBlank String message
) {
}
