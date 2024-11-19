package com.system.announcement.dtos.chat;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StartChatDTO(
        @NotNull UUID announcement
){}
