package com.system.announcement.dtos.chat;

import jakarta.validation.constraints.NotBlank;

public record ReceiveMessageDTO(
        @NotBlank String message
) {
}
