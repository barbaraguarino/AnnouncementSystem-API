package com.system.announcement.dtos.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReceiveMessageDTO(
        @NotNull UUID chat,
        @NotBlank String message
) {
}
